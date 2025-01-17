# Python bindings for the C ACI API

This module provides a Python interface to communicate with OEM-licensed ACI servers via the C SDK.

It is split into two submodules:
  - `wrapper.py` contains classes that include Python bindings for targeted function in the C ACI API shared library, and
  - `client.py` contains a simple `call()` function that returns the server's response, usually as XML or JSON.

The various scripts in the `scripts` directory, provide examples, some of which we will step through below.

---

- [Setup](#setup)
- [Writing a client](#writing-a-client)
- [Run your client program](#run-your-client-program)
- [Response formats](#response-formats)
- [Conclusion](#conclusion)
- [See also](#see-also)

---

## Setup

To run the Python ACI wrapper, install Python 3.X and the following required package:

```sh
pip install python-dotenv
```

In the `lib` sub-directory, please find and open the `.env` file to ensure that the `ACI_LIB_PATH` environment variable points to your C SDK directory, *e.g.*:

```ini
ACI_LIB_PATH="C:\\MicroFocus\\IDOLCSDK_12.13.0_WINDOWS_X86_64"
```

> NOTE: this directory must contain the two files `client.h` and `aciclient.dll`.

## Writing a client

With your IDOL Eduction Server (or other ACI Server) running, our first request will be a simple one to get the version of the running server.  See the script to do this `resources/aci_api/Python/scripts/get_version_oem.py`.  The `call()` function is copied below:

```py
response = aci.client.call(
  encryption_key="<REPLACE_WITH_MY_LONG_KEY_STRING>",
  hostname="127.0.0.1",
  port="<REPLACE_WITH_ACI_PORT>",
  aci_action="GetVersion"
)
print(response)
```

Navigate to this directory and edit the file to make the following modifications for your setup:

1. set the value of the `encryption_key` parameter to the long string found in your `OEMstring.txt` file, and

    ```diff
    - encryption_key="<REPLACE_WITH_MY_LONG_KEY_STRING>",
    + encryption_key="NTI6MXyE...3dheAQC"
    ```

1. set the value of the ACI port, e.g. for IDOL Eduction Server:

    ```diff
    - port="<REPLACE_WITH_ACI_PORT>",
    + port=13000,
    ```

    > NOTE: Where the IDOL Eduction Server ACI port is configured in `eductionserver.cfg` in the `Server` section as:
    > ```ini
    > [Server]
    > Port=13000
    > ```

## Run your client program

First, ensure that IDOL Eduction Server is running: double-click the `eductionserver.exe` (or the equivalent executable for your preferred ACI server). 

Now, you're ready to run your client:

```sh
> cd resources\aci_api\Python\scripts
> python get_version_oem.py
Eduction Server v12.13.0
```

## Response formats

If you uncomment line 14 (as follows) and re-run the script you will see the response from Eduction Server is an XML string.

```diff
- # print(response)
+ print(response)
```

As an extra exercise, why not modify the Python code to capture other properties from the response XML?

You can optionally configure a response in alternative formats including JSON.  See the [reference guide](https://www.microfocus.com/documentation/idol/IDOL_12_13/EductionServer_12.13_Documentation/Help/Content/Actions/SharedParameters/_ACI_ResponseFormat.htm) for more details.

Next, let's run the included `get_version_oem_json.py` script, which adds an additional option to specify the response format:

```py
aci_params = {
  "ResponseFormat": "simplejson"
}
```

Run this command to see the same output as before:

```sh
> python get_version_oem_json.py
Eduction Server v12.13.0
```

Notice that this second script is obtaining these values now from response JSON, as follows:

```py
name = data["autnresponse"]["responsedata"]["commonname"]
version = data["autnresponse"]["responsedata"]["version"]
```

## Conclusion

You now understand how to setup and run a Python ACI Client to interact with an IDOL ACI Server.

Next, why not explore some of the other methods for interacting with ACI Servers [here](../../../tutorials/aci_api/README.md#capability-showcase).

## See also

- ACI API Programming Guide [C Language Interface](https://www.microfocus.com/documentation/idol/IDOL_12_13/IDOLCSDK_12.13_Documentation/Guides/html/Content/C/c_part.htm).
