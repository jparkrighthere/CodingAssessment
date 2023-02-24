# Transactions Program

This program reads in a CSV file of transactions and spends the given number of points, following the rules outlined in the prompt. It then prints out the remaining point balances for each payer.

## Requirements

- Java 8 or higher

## Usage

To run the program, open a terminal and navigate to the directory where the `transactions.java` file is located. Then run the following command:


Replace `<points>` with the number of points to spend, and `<filename.csv>` with the name of the CSV file to read.

## CSV Format

The CSV file should have the following format:


Each row should represent a transaction, with the payer name, the number of points (positive or negative), and the timestamp in ISO 8601 format. The header row should be omitted.

## Output

The program will print out the remaining point balances for each payer in the following format:


This indicates that DANNON has 1000 points remaining, while UNILEVER and MILLER COORS have 0 points remaining.

## Implementation

The program uses a `Map` to keep track of the point balances for each payer, and a `List` to store the transaction information. The program first reads in the CSV file and populates these data structures, then sorts the transactions by timestamp and spends the given number of points according to the rules outlined in the prompt. Finally, the program prints out the remaining point balances for each payer.

## License

Jeonghyeon Park
