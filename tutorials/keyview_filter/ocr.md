# Optical Character Recognition (OCR)

Not all documents already contain machine usable text. These types of documents may be scans, screenshots or pictures.

When processing raster image files, KeyView can perform Optical Character Recognition (OCR) to attempt to filter text that might be visible in the image. As of the 12.13.0 release, 46 languages covering Latin, Arabic, Chinese, Cyrillic and a few other alphabets.  See the [OCR Supported Languages](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/c-programming/index.html#Shared/_KV_OCR_Supported_Languages.htm) appendix for more details.

In this lesson, you will:
- perform OCR on a PNG image file
- gain awareness of available OCR optimization options

> NOTE: This guide assumes you have already completed the introductory KeyView Filter [tutorial](./introduction.md#keyview-filter-sdk-introduction).

---

- [Setup](#setup)
  - [Resources](#resources)
- [Perform OCR](#perform-ocr)
  - [Perform OCR with `filter`](#perform-ocr-with-filter)
  - [Perform OCR with `filtertest`](#perform-ocr-with-filtertest)
- [Conclusion](#conclusion)
- [See also](#see-also)
  
---

## Setup

Before you continue with this lesson, refer to the [documentation links](#see-also) below.

> NOTE: This lesson assumes you have already completed the [KeyView Filter SDK introduction](../keyview_filter/introduction.md#keyview-sdk-introduction) lesson covering essential setup steps (*e.g.* required downloads and installation steps) and basic KeyView Filter SDK concepts.

### Resources

Be sure to download the following resources before you continue:
- sample documents from [here](../../resources/keyview_filter/) and install to `C:\MicroFocus\idol-oem-tutorials\resources`

## Perform OCR

### Perform OCR with `filter`

```sh
> cd C:\MicroFocus\KeyviewFilterSDK_12.13.0_WINDOWS_X86_64\WINDOWS_X86_64\bin
> filter ..\..\..\idol-oem-tutorials\resources\keyview_filter\screenshot_TO_OCR.png ocr.txt
filter: ..\..\..\idol-oem-tutorials\resources\keyview_filter\screenshot_TO_OCR.png to ocr.txt
filter: error code returned is KVERR_Success
```

> NOTE: The `KVERR_Success` error code is a positive result where the destination `hidden_text` file contains the output.  Use your UTF-8 capable text editor, so you can properly view the output - just in case complex character sets are represented in the test document.

View `ocr.txt` to see the OCR'd text output and also view `screenshot_TO_OCR.png` for reference. There may be some mistakes in the OCR'd text. OCR even for machine generated text isn't perfect.  Handwritten text has limited support and cursive text is not supported.

> NOTE: OCR is relatively CPU intensive process and takes extra time to process an image file vs doing no OCR.

Try `filter` performing OCR with your own image files.  Remember you may need to extract sub-files using `tstxtract` with `formats.ini [Options] ExtractImages=true` as discussed in the [Sub-file Extraction](./introduction.md#sub-file-extraction](https://github.houston.softwaregrp.net/christopher-blanks/idol-oem-tutorials/blob/api_lessons/tutorials/keyview_filter/introduction.md) lesson in the introduction.

### Perform OCR with `filtertest`

`filtertest` lets you skip OCR for supported and configure some available options with the following command line options:
```
    [-nocr] do not perform OCR on raster images
    [-ocr languages orientation detectAlphabet] set OCR options (enums are passed in as ints)
```

```sh
> cd C:\MicroFocus\KeyviewFilterSDK_12.13.0_WINDOWS_X86_64\WINDOWS_X86_64\bin
> filtertest -nocr ..\..\..\idol-oem-tutorials\resources\keyview_filter\screenshot_TO_OCR.png ocr.txt
filtertest: ..\..\..\idol-oem-tutorials\resources\keyview_filter\screenshot_TO_OCR.png to ocr.txt
filtertest: Target CharSet is 53
FilterFile: error code returned is KVERR_Success
Extended error code is 0
filtertest finished with RC: 0
```

> NOTE: `RC: 0` is a positive result equivalent to `KVERR_Success` where the destination `restrictions` file contains the output. Use your UTF-8 capable text editor, so you can properly view the output - just in case complex character sets are represented in the test document.

The `ocr.txt` output file will contain no text since the `-nocr` flag was added.

Read up on the `languages orientation detectAlphabet` tuning options in the [Optical Character Recognition](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/c-programming/index.html#filter_shared/OCR.htm) section of the programming guide.

## Conclusion

With KeyView Filter SDK supporting the OCR of images, more content is available for downstream processing.

Next, why not try more tutorials to explore some of the other features available in KeyView Filter SDK, linked from the [main page](../README.md#keyview-filter-sdk-showcase).

## See also

- [KeyView Filter SDK C Programming Guide - OCR](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/c-programming/index.html#filter_shared/OCR.htm)
- [KeyView Filter SDK C++ Programming Guide - OCR](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/cpp-programming/index.html#filter_shared/Optical_Character_Recognition.htm)
- [KeyView Filter SDK Java Programming Guide - OCR](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/java-programming/index.html#filter_shared/OCR.htm)
- [KeyView Filter SDK .NET Programming Guide - OCR](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/dotnet-programming/index.html#filter_shared/Optical_Character_Recognition.htm)
[Wikipedia - Optical Character Recognition](https://en.wikipedia.org/wiki/Optical_character_recognition)
