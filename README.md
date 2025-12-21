# Budgeteer
## Releases
### Version 1.1
- Decimals are now separated using periods rather than spaces.
- Groupings of 1,000 are now separated with a single low-9 quotation mark "‚" rather than a space, as it resembles a standard comma but will not cause errors in CSV data.
- Directly adjacent rows with the same title will now be combined into a single row.
- Error stack traces will no longer be printed when a command is misused.
- Row IDs will no longer be displayed with their Row's value added.
- The total value of the whole table, or portions of it when a regex is provided, will be displayed alongside the table.
### Version 1.0
Initial release.
## Introduction
Budgeteer is a simple quick-and-dirty CLI expense tracking application written in pure Java. This project
has no third-party dependencies and is available as a JAR located in `dist/`. I wrote this as a personal
budgeting tool.

This project is currently being overhauled. The source in this repository does not compile to the previous
distributions, though said distributions are still functional. Reference previous versions of this repository
for accurate documentation of v1.0 and v1.1.

## License
This project is licensed under the permissive Zero-Clause BSD License. Copyright 2025, Xavier Mercerweiss.
 
