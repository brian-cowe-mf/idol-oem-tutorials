package com.autonomy.aci.samples;

import com.autonomy.aci.client.services.AciServiceException;
import com.autonomy.aci.client.services.AciErrorException;
import com.autonomy.aci.client.services.AciService;
import com.autonomy.aci.client.services.impl.AciServiceImpl;
import com.autonomy.aci.client.services.impl.DocumentProcessor;
import com.autonomy.aci.client.transport.AciParameter;
import com.autonomy.aci.client.transport.AciServerDetails;
import com.autonomy.aci.client.transport.InputStreamActionParameter;
import com.autonomy.aci.client.transport.impl.AciHttpClientImpl;
import com.autonomy.aci.client.transport.impl.BteaEncryptionCodec;
import com.autonomy.aci.client.util.ActionParameters;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.SAXException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MSProcess {

    private static final Logger LOG = Logger.getLogger(MSProcess.class.getName());

	private static boolean USE_IDOL_OEM_ENCRYPTION = true;
	private static final String IDOL_OEM_ENCRYPTION_KEY_ENV_VAR = "ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY";
	private static final String IDOL_OEM_ENCRYPTION_KEY_STUB_VALUE = "REPLACE_WITH_MY_LONG_KEY_STRING";
	private static final String IDOL_OEM_ENCRYPTION_KEY = IDOL_OEM_ENCRYPTION_KEY_STUB_VALUE;

	static class Args {
        static final int EXPECTED_NUM_ARGS = 4;
		static final int MAX_INSERT_XML_SIZE_BYTES = 10000;

		final String aciHost;
		final Integer aciPort;
		final String configName;
		final String source;
		
		//Prints program usage information to standard output
		private void displayusageinfo()
		{
			System.console().printf("Usage: MSProcess <HOSTNAME/IP> <ACI_PORT_#> <CONFIG_NAME> <SOURCE>\n");
			System.console().printf("Parameters:\n");
			System.console().printf("    <HOSTNAME/IP>	Hostname / IP address of ACI service.\n");
			System.console().printf("    <ACI_PORT_#>	ACI port number of ACI service.\n");
			System.console().printf("    <CONFIG_NAME>	Media Server session configuration name.\n");
			System.console().printf("    <SOURCE>	Media source file path to process.\n");
		}


		Args(final String[] stringArgs) throws IllegalArgumentException {
			if (stringArgs.length != EXPECTED_NUM_ARGS) {
				displayusageinfo();
				throw new IllegalArgumentException("Wrong number of arguments (4 required).");
			}
			
			aciHost = stringArgs[0];
			aciPort = Integer.parseInt(stringArgs[1]);
			configName = stringArgs[2];
			source = stringArgs[3];

			System.console().printf("ACI Host: %s\n", aciHost);
			System.console().printf("ACI Port: %d\n", aciPort);
			System.console().printf("Configuration name: %s\n", configName);
			System.console().printf("Source file path: %s\n", source);
		}
	}

	private static String getStringFromDocument(Document doc) throws Exception
	{
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");		
		transformer.transform(new DOMSource(doc), result);

		return writer.toString();
	}

	private BteaEncryptionCodec get_encryptionCodec(String key)
	{
		BteaEncryptionCodec encryptionCodec = null;
		
		try {
			LOG.info("OEM encryption key(s): " + key);
			Pattern pattern = Pattern.compile("(\\d*),(\\d*),(\\d*),(\\d*)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(key);
			if (matcher.find())
			{
				long[] keys = new long[4];
				keys[0] = Long.parseLong(matcher.group(1));
				keys[1] = Long.parseLong(matcher.group(2));
				keys[2] = Long.parseLong(matcher.group(3));
				keys[3] = Long.parseLong(matcher.group(4));
		
				encryptionCodec = new BteaEncryptionCodec(keys);
			} else {
				encryptionCodec = new BteaEncryptionCodec(key);
			}
		} catch(Exception e) {
			LOG.severe("Unable to set up API encryption.\n");
			throw e;
		} finally {
			return encryptionCodec;
		}
	}
	
    private void ms_process(String[] args) throws Exception
	{
		Args parsedArgs;
		final AciService aciService;
		final ActionParameters parameters;
		final Document response;

		try {
			parsedArgs = new Args(args);
		} catch(Exception e) {
			LOG.severe("Unable to parse arguments.\n");
			throw e;
		}
		
		try {
			final AciServerDetails aciServerDetails = new AciServerDetails(parsedArgs.aciHost, parsedArgs.aciPort);
			final BteaEncryptionCodec encryptionCodec;

			// Initialize OEM licensing
			if (USE_IDOL_OEM_ENCRYPTION)
			{
				String key = System.getenv(IDOL_OEM_ENCRYPTION_KEY_ENV_VAR);

				if (key != null)
				{
					LOG.info("Using OEM encryption key from environment variable: " + IDOL_OEM_ENCRYPTION_KEY_ENV_VAR);
					if (key.equals(IDOL_OEM_ENCRYPTION_KEY_STUB_VALUE)) {
						LOG.warning("Don't forget to correctly set " + IDOL_OEM_ENCRYPTION_KEY_ENV_VAR);
						throw new Exception("Invalid OEM encryption key");
					} else {
						encryptionCodec = get_encryptionCodec(key);
					}
				} else {
					LOG.info("Using OEM encryption key from constant IDOL_OEM_ENCRYPTION_KEY");
					if (IDOL_OEM_ENCRYPTION_KEY.equals(IDOL_OEM_ENCRYPTION_KEY_STUB_VALUE)) {
						LOG.warning("Don't forget to correctly set constant: IDOL_OEM_ENCRYPTION_KEY");
						throw new Exception("Invalid OEM encryption key");
					} else {
						encryptionCodec = get_encryptionCodec(IDOL_OEM_ENCRYPTION_KEY);
					}
				}

				aciServerDetails.setEncryptionCodec(encryptionCodec);				
			}

			AciHttpClientImpl aciHttpClientImpl = new AciHttpClientImpl(HttpClientBuilder.create().build());
			aciHttpClientImpl.setUsePostMethod(true);
			aciService = new AciServiceImpl(aciHttpClientImpl, aciServerDetails);			
		} catch(Exception e) {
			LOG.severe("Unable to set up AciService.\n");
			throw e;
		}

		try {
			// Set action parameters
			parameters = new ActionParameters("PROCESS");
			parameters.add("configName", parsedArgs.configName);
			parameters.add("source", parsedArgs.source);
			parameters.add("responseFormat", "xml");
		} catch(Exception e) {
			LOG.severe("Unable to set up ActionParameters.\n");
			throw e;
		}

		try {
			LOG.info("Executing action...");

			response = aciService.executeAction(parameters, new DocumentProcessor());
		} catch(Exception e) {
			LOG.severe("Unable to execute action.\n");
			throw e;
		}

		try {
			LOG.info("Handling response...");

			String responseStr = getStringFromDocument(response);
			System.console().printf("\n" + responseStr + "\n");
			
			// Get <response> and print it
			final XPath xpath = XPathFactory.newInstance().newXPath();

			System.console().printf("response: %s\n", xpath.evaluate("/autnresponse/response", response));

			System.console().printf("Token: %s\n", xpath.evaluate("/autnresponse/responsedata/token", response));
		} catch(Exception e) {
			LOG.severe("Unable to handle response.\n");
			throw e;
		}
    }

    public static void main(String[] args) throws Exception
    {
		LOG.info("Program loaded");

        MSProcess obj = new MSProcess();

        try {
            obj.ms_process(args);
        } catch(Exception e) {
            LOG.severe("Scary exception happened: " + e.getMessage());
            throw e;
        }
    }
}
