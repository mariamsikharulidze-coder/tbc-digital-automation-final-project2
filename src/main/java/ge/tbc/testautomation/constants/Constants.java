package ge.tbc.testautomation.constants;

public final class Constants {

    // URLs
    public static final String
            HOME_URL = "https://tbcbank.ge/en",
            GEORGIAN_HOME_URL = "https://tbcbank.ge/ka",
            TBC_CREDIT_URL_REGEX = "^https://tbccredit\\.ge/.*",
            POS_TERMINAL_ORDER_URL_REGEX = ".*/en/pos-terminals/order/pos/5/1.*";

    // API
    public static final String
            API_BASE_URL = "https://apigw.tbcbank.ge",
            MONEY_TRANSFER_FEES_ENDPOINT = "/api/v1/moneyTransfer/fees",
            CONSUMER_LOAN_PAGE_ENDPOINT = "/api/v1/sites/pages/VL9d8DnAnqAGWv84sUJvZ",
            EN_US_LOCALE = "en-US",
            GET_METHOD = "GET";

    public static final int
            OK_STATUS_CODE = 200,
            BAD_REQUEST_STATUS_CODE = 400;

    // Samomxmareblo Loan
    public static final String
            CONSUMER = "Consumer",
            LOANS_CALCULATOR = "Loans Calculator",
            BY_AMOUNT = "By Amount",
            AMOUNT = "Amount",
            MONTH = "Month",
            INTEREST_RATE = "Interest rate",
            EFFECTIVE_INTEREST_RATE = "Effective Interest rate",
            APPLY = "Apply",
            LOAN_AMOUNT = "3000",
            LOAN_PERIOD = "48",
            MONTHLY_CONTRIBUTION = "75.94",
            EXPECTED_INTEREST_RATE = "From 9.9%",
            EXPECTED_EFFECTIVE_INTEREST_RATE = "From 18%",
            TABS_SECTION = "tabsSection";

    // Gzavnilebi
    public static final String
            MONEY_TRANSFERS = "Money Transfers",
            FEE_CALCULATION = "Remittance Fee Calculation",
            DEFAULT_CURRENCY = "GEL",
            CURRENCY = "EUR",
            COUNTRY_DROPDOWN = "Choose a country",
            COUNTRY = "Georgia",
            COUNTRY_CODE = "GEO",
            TRANSFER_AMOUNT = "200",
            MONEY_GRAM = "MoneyGram",
            INTEL_EXPRESS = "IntelExpress",
            FAST_TRANSFER = "FastTransfer";

    public static final double
            MONEY_GRAM_FEE = 2.0,
            INTEL_EXPRESS_FEE = 1.0,
            FAST_TRANSFER_FEE = 5.0;

    // Ganvadeba
    public static final String
            INSTALLMENTS = "Installments",
            TERMS = "Terms",
            INSTALLMENT_CARD_TEXT = "Installment Buy any item from",
            LOAN_LIMIT = "Loan limit";

    // POs terminals
    public static final String
            POS_TERMINALS_PATH = "/en/business/payment-systems/pos-terminals",
            ANDROID_TERMINAL = "Android Terminal",
            FILL_OUT_FORM = "fill out the form";

    //navigacia
    public static final String
            PERSONAL = "Personal",
            FOR_BUSINESS = "For Business";

    private Constants() {
    }
}