# Extract Table Oriented Data

Eduction has a mode called `Table mode` that makes Eduction aware of columns and column headings.  Spreadsheet and CSV files are examples of data that represent data in rows and columns, typically with column headings.  With Eduction `Table mode`, matching will be able to differentiate between say a `date of birth` and `last updated date` because of the column heading.

For more details on this topic, reference the [Extract Entities from Tables](https://www.microfocus.com/documentation/idol/IDOL_12_13/EductionSDK_12.13_Documentation/Guides/html/Content/UseEduction/ImproveMatches/Extract_Table_Data.htm?Highlight=table) section of the Eduction reference guide.

In this lesson, you will:
- Perform `table mode` extraction with `edktool`
- Explore the input resources and output results for the table extraction sample.
- Optionally experiment with changes to `input.csv` and see if/how output changes.

> NOTE: This guide assumes you have already completed the introductory Eduction [tutorial](./introduction.md#eduction-sdk-introduction).

---

- [Setup](#setup)
  - [Resources](#resources)
- [Perform `table mode` extraction](#perform-table-mode-extraction)
  - [`input\input.csv`](#inputinputcsv)
  - [`config\test.cfg`](#configtestcfg)
  - [`grammar\test.xml`](#grammartestxml)
  - [`out.xml`](#outxml)
- [Next Steps (optional)](#next-steps-optional)
- [PII `table mode` example - Basic](#pii-table-mode-example---basic)
  - [Perform PII `table mode` extraction - Basic](#perform-pii-table-mode-extraction---basic)
- [PII `table mode` example - Advanced](#pii-table-mode-example---advanced)
  - [Perform PII `table mode` extraction - Advanced](#perform-pii-table-mode-extraction---advanced)
- [Conclusion](#conclusion)
- [See also](#see-also)

---

## Setup
Before you continue with this lesson, refer to the [documentation links](#see-also) below.

> NOTE: This lesson assumes you have already completed the [Eduction SDK introduction](../eduction/introduction.md#eduction-sdk-introduction) lesson covering essential setup steps (*e.g.* required downloads and installation steps) and basic Eduction concepts.

### Resources

Be sure to download the following resources before you continue:
- [PII edk_samples](../../resources/eduction/pii/edk_samples) and install to `C:\MicroFocus\EductionGrammars_12.13.0_COMMON\pii\edk_samples\resources`

## Perform `table mode` extraction

The Eduction SDK contains a working example under `samples\table_extraction`. Since this lesson uses `edktool` to perform the extraction only the `samples\table_extraction\resources` sub-folders are of interest for now.

Go ahead and perform extract with `edktool` and we'll explore the inputs and output in more detail below.

```sh
> cd  C:\MicroFocus\EductionSDK_12.13.0_WINDOWS_X86_64\samples\table_extraction\resources
> edktool extract -l ..\..\..\licensekey.dat -c test\config\test.cfg -i test\input\input.csv -o out.xml
```

Let's connect the dots between the `test\config\test.cfg`, `test\grammar\test.xml` and `test\input\input.csv'.

### `input\input.csv`

The `test\input\input.csv` is pretty simple containing 3 columns and 4 rows with the first row containing the header and the rest representing the data.
```
Name,Number,Location
Adam,1,London
Ben,92,New York
Charles,pi,Peckham
```

### `config\test.cfg`

The `test\config\test.cfg` is also pretty simple, but contains some unique settings particular to `table mode`
```
[Eduction]
ResourceFiles=../resources/test/grammar/test.xml
HeaderEntity0=simple_pii/name_header
CellEntity0=simple_pii/name
HeaderEntity1=simple_pii/number_header
CellEntity1=simple_pii/number
HeaderEntity2=simple_pii/location_header
CellEntity2=simple_pii/location
MatchCase=FALSE
AllowMultipleResults=All
EnableComponents=TRUE
```

The `HeaderEntity#` and `CellEntity#` reference the header and value entities as defined in `test\grammar\test.xml`.

> NOTE: If you're curious, why `ResourceFiles` reference test.xml vs test.ecr, both the XML source and compiled ECR are supported. When XML source is used it gets compiled as the EDKEngine is initialized.  This is functional but less efficient than compiled ECR.

### `grammar\test.xml`

The `test\grammar\test.xml` is pretty simple defining 3 column headers and 3 value entities that correspond with the header entities.

### `out.xml`

The `out.xml` has matches for all 3 column headers, 3 matches for `name`, 2 matches for `number` and 2 matches for `location`.  This is because 'pi' isn't a number and 'Peckham' isn't defined in the `location` entity.

## Next Steps (optional)

If you'd like you can make changes to `test.xml` and/or `input.csv`.  Here's some ideas:
- Create a fourth column called age and put numbers in it. Hint: none of those number values will match since only the 'number' column allows for numbers.
- Add fictitious locations like 'Charles Town' or 'Mount Adam' to `input.csv` to the 'location' column.  Hint: these values will not match since only the 'name' column allows for numbers.

## PII `table mode` example - Basic

In the Eduction SDK documentation section [Extract Entities from Tables](https://www.microfocus.com/documentation/idol/IDOL_12_13/EductionSDK_12.13_Documentation/Guides/html/Content/UseEduction/ImproveMatches/Extract_Table_Data.htm?Highlight=cell) the PII date.ecr is used in the example.  This lesson builds upon the documentation including PII `national_id.ecr` in the configuration and providing sample `input.csv` file.

In the [setup](#setup), you downloaded the `pii_table_mode` resources. Two Eduction configuration files are provided: 
- `pii_table_mode.cfg` - enables entities from date.ecr and national_id.ecr
- `date.cfg` - enables date entities like `pii_table_mode.cfg` but without the `table mode` configuration

The `input.csv` file has two date columns: "Date of Birth" and "Admission Date" as one might see in medical or health insurance records. For the purpose of this lesson only the "Date of Birth" column and the "SSN" are of interest.  The "Name" column and thus `name.ecr` are excluded intentionally from this lesson to simplify things. In practice, for PII and PHI use cases, name, national id and date of birth would all be of interest.

### Perform PII `table mode` extraction - Basic

Run `edktool extract` using the same `input.csv` file with each of the `config` folder configurations.

```sh
> cd  C:\MicroFocus\EductionGrammars_12.13.0_COMMON\pii\edk_samples\resources
> edktool extract -l ..\..\..\..\EductionSDK_12.13.0_WINDOWS_X86_64\licensekey.dat -c pii_table_mode\config\pii_table_mode.cfg -i pii_table_mode\input\input.csv -o table_mode.xml

> edktool extract -l ..\..\..\..\EductionSDK_12.13.0_WINDOWS_X86_64\licensekey.dat -c pii_table_mode\config\date.cfg -i pii_table_mode\input\input.csv -o no_table_mode.xml
```

You will get the following matches:
- `no_table_mode.xml`: 4 total matches with 2 for the dates of birth and 2 for the admission date.
- `table_mode.xml`: 6 total matches with 2 for the landmarks, 2 for the dates of birth and 2 for the SSNs.

Eduction `table mode` was able to distinguish between the 2 types of dates by keying of the CSV column header. It performed similarly for the U.S. national ID # (SSN).

## PII `table mode` example - Advanced

In release 12.13, Eduction improved its smarts when it comes to recognizing table schemas:
1. increased tolerance to when the header row begins and configurable with `MaxSearchHeaderRow`
2. ability to detect a different schema across spreadsheet sheets

### Perform PII `table mode` extraction - Advanced

`PII_table.xlsx` can be found in the `pii_table_mode\extras` folder and is available for your reference.  This test file has 3 sheets specifically constructed to exploit advanced Eduction `table mode` features:
- `1- sensitive data`: empty cells in A4 & B5
- `2- extra column`: extra column B, shifting sensitive data over
- `2- extra header`: extra stuff before header row, empty cells in C5 & A6

> NOTE:  Remember Eduction SDK accepts UTF-8 encoded text as its input, so `PII_table.xlsx` must be first processed into a UTF-8 text file.

There are 2 files in the `pii_table\input` folder which were produced with KeyView Filter SDK 12.13.0 using new settings available in KeyView.
- `PII_table.xlsx.KV_TEXT_TD+OTD=F.TXT` - output is like previous versions of KeyView with no specific sheet delimiter, nor consistent column delimiters
- `PII_table.xlsx.KV_TEXT_TD+OTD=T.TXT` - output has the new start / end table delimiters and consistent tabs across empty columns

> NOTE: Refer to the [Extract Entities from Tables](https://www.microfocus.com/documentation/idol/IDOL_12_13/EductionSDK_12.13_Documentation/Guides/html/Content/UseEduction/ImproveMatches/Extract_Table_Data.htm) in the 12.13 Eduction SDK documentation for more details. 

Run `edktool extract` first using `PII_table.xlsx.KV_TEXT_TD+OTD=F.TXT` and then `PII_table.xlsx.KV_TEXT_TD+OTD=T.TXT` with the `pii_table_mode\config\pii_table_mode.cfg`.

```sh
> cd  C:\MicroFocus\EductionGrammars_12.13.0_COMMON\pii\edk_samples\resources
> edktool extract -l ..\..\..\..\EductionSDK_12.13.0_WINDOWS_X86_64\licensekey.dat -c pii_table_mode\config\pii_table_mode.cfg -i pii_table_mode\input\PII_table.xlsx.KV_TEXT_TD+OTD=F.TXT -o table_mode_F.xml

> edktool extract -l ..\..\..\..\EductionSDK_12.13.0_WINDOWS_X86_64\licensekey.dat -c pii_table_mode\config\date.cfg -i pii_table_mode\input\PII_table.xlsx.KV_TEXT_TD+OTD=T.TXT -o table_mode_T.xml
```
There will be a mix of true positive, false positive and false negative matches across the 3 sheets in the `table_mode_F.xml` output due to the lack of consistent column delimiters and lack of a sheet delimiter.  In `table_mode_T.xml`, all 3 sheets will produce matches with 3 names, 3 SSNs and 4 DOBs, plus the 3 column headers on each of the 3 sheets. 

As extra credit, user `edktool` to process `PII_table.xlsx.KV_TEXT_TD+OTD+SCF=T.TXT` where it was produced with KeyView Filter SDK's [`Standardize Cell Formats`](https://www.microfocus.com/documentation/idol/IDOL_12_13/KeyviewFilterSDK_12.13_Documentation/Guides/html/c-programming/index.html#Shared/_KV_Standardize_Cell_Formats.htm) option enabled.

## Conclusion

You now understand the basics and advanced capabilities of Eduction `table mode` and how it can reduce false positives for spreadsheet and CSV type data.  You have also explored a PII/PHI use case for `table mode` with a simple, but still useful configuration.

Next, why not try more tutorials to explore some of the other features available in IDOL Eduction, linked from the [main page](../README.md#capability-showcase-examples).

## See also

- [IDOL Eduction SDK User and Programming Guide](https://www.microfocus.com/documentation/idol/IDOL_12_13/EductionSDK_12.13_Documentation/Guides/html)
