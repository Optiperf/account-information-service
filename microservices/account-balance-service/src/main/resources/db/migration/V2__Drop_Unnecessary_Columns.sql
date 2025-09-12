-- Check if the columns contain any data
SELECT * FROM account_balance WHERE account_name IS NOT NULL OR account_type IS NOT NULL OR currency IS NOT NULL OR status IS NOT NULL;

-- If no data is found, proceed to drop the columns
ALTER TABLE account_balance
DROP COLUMN account_name,
DROP COLUMN account_type,
DROP COLUMN currency,
DROP COLUMN status;
