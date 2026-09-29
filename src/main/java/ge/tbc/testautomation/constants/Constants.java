package ge.tbc.testautomation.constants;

public final class Constants {

    // URLs
    public static final String HOME_URL = "https://tbcbank.ge/en";
    public static final String GEORGIAN_HOME_URL = "https://tbcbank.ge/ka";
    public static final String TBC_CREDIT_URL_REGEX =
            "^https://tbccredit\\.ge/.*";
    public static final String POS_TERMINAL_ORDER_URL_REGEX =
            ".*/en/pos-terminals/order/pos/5/1.*";


    // API
    public static final String API_BASE_URL =
            "https://apigw.tbcbank.ge";
    public static final String MONEY_TRANSFER_FEES_ENDPOINT =
            "/api/v1/moneyTransfer/fees";
    public static final String CONSUMER_LOAN_PAGE_ENDPOINT =
            "/api/v1/sites/pages/VL9d8DnAnqAGWv84sUJvZ";
    public static final String EN_US_LOCALE = "en-US";

    public static final String GET_METHOD = "GET";

    public static final int OK_STATUS_CODE = 200;
    public static final int BAD_REQUEST_STATUS_CODE = 400;


    // For loan
    public static final String CONSUMER = "Consumer";
    public static final String LOANS_CALCULATOR = "Loans Calculator";
    public static final String BY_AMOUNT = "By Amount";

    public static final String AMOUNT = "Amount";
    public static final String MONTH = "Month";
    public static final String INTEREST_RATE = "Interest rate";
    public static final String EFFECTIVE_INTEREST_RATE =
            "Effective Interest rate";

    public static final String APPLY = "Apply";

    public static final String LOAN_AMOUNT = "3000";
    public static final String LOAN_PERIOD = "48";
    public static final String MONTHLY_CONTRIBUTION = "75.94";

    public static final String EXPECTED_INTEREST_RATE = "From 9.9%";
    public static final String EXPECTED_EFFECTIVE_INTEREST_RATE = "From 18%";

    public static final String TABS_SECTION = "tabsSection";



    public static final String MONEY_TRANSFERS = "Money Transfers";
    public static final String FEE_CALCULATION =
            "Remittance Fee Calculation";

    public static final String DEFAULT_CURRENCY = "GEL";
    public static final String CURRENCY = "EUR";

    public static final String COUNTRY_DROPDOWN =
            "Choose a country";
    public static final String COUNTRY = "Georgia";
    public static final String COUNTRY_CODE = "GEO";

    public static final String TRANSFER_AMOUNT = "200";

    public static final String MONEY_GRAM = "MoneyGram";
    public static final String INTEL_EXPRESS = "IntelExpress";
    public static final String FAST_TRANSFER = "FastTransfer";

    public static final double MONEY_GRAM_FEE = 2.0;
    public static final double INTEL_EXPRESS_FEE = 1.0;
    public static final double FAST_TRANSFER_FEE = 5.0;


    public static final String INSTALLMENTS = "Installments";
    public static final String TERMS = "Terms";
    public static final String INSTALLMENT_CARD_TEXT =
            "Installment Buy any item from";
    public static final String LOAN_LIMIT = "Loan limit";


    public static final String POS_TERMINALS_PATH =
            "/en/business/payment-systems/pos-terminals";
    public static final String ANDROID_TERMINAL =
            "Android Terminal";
    public static final String FILL_OUT_FORM =
            "fill out the form";


    private Constants() {
    }
}