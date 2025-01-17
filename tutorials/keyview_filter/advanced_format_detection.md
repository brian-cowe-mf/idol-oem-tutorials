# Advanced Format Detection

As we saw in the introduction lesson for KeyView Filter SDK, the format type (and class) and other attributes can be automatically detected for files.  KeyView relies on a format signature to uniquely identify the format code (with >1750 supported as of the 12.13 release).  Relying on a file extension is dangerous and can result in mis-processing with extra downstream processing costs.  Encryption & password protection often limits the visibility inside files.  KeyView does its best to uniquely identify protected files and also set a separate flag indicating encryption status.

In this lesson, you will:
- explore not relying on the file extension 
- explore password / encryption detection

> NOTE: This guide assumes you have already completed the introductory KeyView Filter [tutorial](./introduction.md#keyview-filter-sdk-introduction).

---

- [Setup](#setup)
  - [Resources](#resources)
- [Don't rely on the file extension](#dont-rely-on-the-file-extension)
  - [Perform detection on SmartCipher file](#perform-detection-on-smartcipher-file)
- [Password / Encryption detection](#password--encryption-detection)
  - [Perform password / encryption detection](#perform-password--encryption-detection)
- [Conclusion](#conclusion)
- [See also](#see-also)

---

## Setup

Before you continue with this lesson, refer to the [documentation links](#see-also) below.

> NOTE: This lesson assumes you have already completed the [KeyView Filter SDK introduction](./introduction.md#keyview-filter-sdk-introduction) lesson covering essential setup steps (*e.g.* required downloads and installation steps) and basic Filter SDK concepts.

### Resources

Be sure to download the following resources before you continue:
- sample documents from [here](../../resources/keyview_filter/) and install to `C:\MicroFocus\idol-oem-tutorials\resources`

> NOTE: This lesson assumes you have already completed the [KeyView Filter SDK introduction](../keyview_filter/introduction.md#keyview-sdk-introduction) lesson covering essential setup steps (*e.g.* required downloads and installation steps) and basic KeyView Filter SDK concepts.

## Don't rely on the file extension

The Micro Focus SmartCipher encrypted file `SmartCipher_Fmt.txt` was a text file originally in this case.  For this lesson, it was copied with `.BOGUS_EXTENSION` appended.

### Perform detection on SmartCipher file

```sh
> cd C:\MicroFocus\KeyviewFilterSDK_12.13.0_WINDOWS_X86_64\WINDOWS_X86_64\bin
> filter -d ..\..\..\idol-oem-tutorials\resources\keyview_filter\SmartCipher_Fmt.txt detect
The file ..\..\..\idol-oem-tutorials\resources\keyview_filter\SmartCipher_Fmt.txt
Class ID:                       8
Format ID:                      1255
Major Version:          0
Attributes:                     1

KWAD: error code returned is KVERR_Success
```

> NOTE: The `KVERR_Success` error code is a positive result.

The file is correctly detected as format code 1255 and as encrypted per `Attributes: 1`.

The same exact result is returned when processing `SmartCipher_Fmt.txt.BOGUS_EXTENSION`.

Try `filter -d` with other file formats. If you have some (or can acquire them), business intelligence (BI) files from Tableau and others are often a `.zip` file containing their respective interesting data. The [resources README.md](../../resources/keyview_filter/README.md#coffee-chaintwbx) for this tutorial contains a link to a Tableau Packaged Workbook (`.TWBX`) file that is technically a `.zip` file which contains a Tableau Workbook file (`.TWB`) (technically XML), data file(s) of varying formats and other resources.

> NOTE: Microsoft DOCX/PPT/XLSX files are technically zip files. Apple iWork are technically Gzip files. OpenOffice files are also technically zip files.  Open example files that you have in a text editor and you'll see 'PK...' in the beginning.  And you will also be able to open most flavors of these files that you find with WinZip, 7-Zip or similar. By doing so, you will get a sense for the complexity in intelligently extract information from any file format.

## Password / Encryption detection

Many file formats allow for their contents to be password protected and/or encrypted.  When possible, Filter SDK will identify this scenario and return this as part of the format detection attributes data.

### Perform password / encryption detection

In the above example with `SmartCipher_Fmt.txt`, the attribute for `encrypted` file was returned.  This lesson will explore this further with a 7-zip file.

```sh
> filter -d ..\..\..\idol-oem-tutorials\resources\keyview_filter\KeyViewFilterSDK_12.12.0_ReleaseNotes_en_PASSWORD.pdf detect
The file ..\..\..\idol-oem-tutorials\resources\keyview_filter\KeyViewFilterSDK_12.12.0_ReleaseNotes_en.pdf.7z
> cd C:\MicroFocus\KeyviewFilterSDK_12.13.0_WINDOWS_X86_64\WINDOWS_X86_64\bin
Class ID:                       8
Format ID:                      387
Major Version:                  400
Attributes:                     0

KWAD: error code returned is KVERR_Success

> filter -d ..\..\..\idol-oem-tutorials\resources\keyview_filter\keyview_filter\KeyViewFilterSDK_12.12.0_ReleaseNotes_en.pdf_PASSWORD.7z detect
The file ..\..\..\idol-oem-tutorials\resources\keyview_filter\keyview_filter\KeyViewFilterSDK_12.12.0_ReleaseNotes_en.pdf_PASSWORD.7z
Class ID:                       8
Format ID:                      387
Major Version:                  400
Attributes:                     1

KWAD: error code returned is KVERR_Success
```

> NOTE: The `KVERR_Success` error code is a positive result.

The password protect version returned `Attributes: 1` meaning `encrypted` per `ENdocAttributes` in `%KEYVIEW_HOME%\include\adinfo.h`.

## Conclusion
  
Knowing a files is encrypted is the next best thing to be able to decrypt and extract its contents.  KeyView Filter SDK provides this level of detail for many file formats.

Relying on a file's extension to determine if and how to further process a given file is dangerous. You may treat gibberish text as plain text processing large amounts of it for no good reason.  Or you may unpack a zip file that is actually one of many other file formats and thus treat the many contained files as something interesting to process when in practice much more intelligence is required to exclude the noise and correctly parse the interesting bits.

Next, why not try more tutorials to explore some of the other features available in KeyView Filter SDK, linked from the [main page](../README.md#keyview-filter-sdk-showcase).

## See also

- [KeyView Filter SDK C Programming Guide](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/c-programming/index.html)
- [KeyView Filter SDK C++ Programming Guide](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/cpp-programming/index.html)
- [KeyView Filter SDK Java Programming Guide](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/java-programming/index.html)
- [KeyView Filter SDK .NET Programming Guide](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/dotnet-programming/index.html)
