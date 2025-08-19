import random

account_types = ['SAVINGS', 'LOAN', 'CHECKING', 'CREDIT']
currencies = ['USD', 'EUR', 'GBP', 'JPY', 'AUD']

with open("data.sql", "w") as f:
    for acc_num in range(520010001, 520020001):
        acc_name = f"account-name-{acc_num}"
        acc_type = random.choice(account_types)
        currency = random.choice(currencies)

        sql = (
            "INSERT INTO account_details "
            "(account_number, account_name, account_type, created_date, currency, status) "
            f"VALUES ({acc_num}, '{acc_name}', '{acc_type}', CURRENT_TIMESTAMP, '{currency}', 'ACTIVE');\n"
        )
        f.write(sql)