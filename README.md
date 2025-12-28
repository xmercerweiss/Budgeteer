# BudgeteerV1
_A newer version of this project is available [here](https://github.com/xmercerweiss/BudgeteerV2)_.
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
BudgeteerV1 is a simple quick-and-dirty CLI budgeting application written in pure Java. This project
has no third-party dependencies and is available as a JAR file, stored in `dist/`. I wrote this just
to explore a budgeting concept I thought-up one day; I understand how primitive and impractical this
project is as a tool of finance.

The application is centered on a table of transactions called "the ledger," which simply
keeps track of the value, title, and ID of various expenses. Each transaction's ID refers to the total
number of cents the user has spent up to and including that transaction. The datetime of each transaction
is not stored, as the application exclusively aims to document the total allocation of the user's
wealth over time.

The title of each transaction is intended to be a simple summary of what the money was spent on,
such as "Groceries," "Rent," or "Entertainment." The application allows the user to view all
transactions whose title matches a given regular expression; for usage see "Commands."

Each transaction pulls from an available "credit" added to by the user, meant to reflect however
much money the user has on hand at the moment. Any amount of credit may be added at any time,
it's just another tool for tracking where money ends up. Additions of credit are not tracked
by the ledger.

The ledger immutably stores transactions in chronological order. Transactions may not be deleted,
reordered, or retitled. The only deletion allowed is the wiping of the entire ledger.

Both the ledger and the user's remaining credit may be stored in `ledger.csv` and `credit` respectively.
Upon saving, these files will be written into the directory from which the user is executing the JAR. Each
file will be automatically read by the program on launch from that given directory. These paths cannot be altered, 
as they are hard-coded into the program's source. 

My recommended installation is that you download the lastest JAR to `~/Budgeteer`, then add the function in `dist/alias.sh` to your `.bash_aliases` file.

## Commands
Below is a table of the commands used to operate the application. I recommend you keep this
available for reference, as no `help` option exists within the program.

| COMMAND             | DESCRIPTION                                                                                                                                                                        |
|---------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `+ <int n>`         | Adds `n` cents to your total credit.                                                                                                                                               |
| `- <int n> <title>` | Removes `n` cents from your total credit, if present, and creates a new transaction titled `title`. Note that `title` may include multiple words.                                  |
| `* [pattern]`       | Prints the entire ledger if no pattern is given, otherwise prints all transactions with a title matching `/.*pattern.*/i`. Additionally prints the total value of all rows listed. |
| `/`                 | Wipes the entire ledger.                                                                                                                                                           |
| `&`                 | Saves the ledger to `ledger.csv` and your credit to `credit`.                                                                                                                      |
| `!`                 | Closes the program.                                                                                                                                                                |


## License
This project is licensed under the permissive Zero-Clause BSD License. Copyright 2025, Xavier Mercerweiss.
 
