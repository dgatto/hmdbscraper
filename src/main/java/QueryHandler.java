import java.util.List;
import java.util.Properties;

public class QueryHandler {
    private QueryHandler() { } // make your constructor private, so the only war
    // to access "application" is through singleton pattern

    private static QueryHandler _app;

    public static QueryHandler getSharedApplication()
    {
        if (_app == null)
            _app = new QueryHandler();
        return _app;
    }

    public Properties main(String range, List<String> categoriesInput, String queryType) {

        Properties builtQuery = new Properties();
        String categories = "";

        //  build categories into a string
        for (int i = 0; i < categoriesInput.size(); i ++) {
            categories = categories + categoriesInput.get(i) + ", ";
        }

        builtQuery.setProperty("queryContent", range);
        builtQuery.setProperty("queryCategories", categories);
        builtQuery.setProperty("queryType", queryType);

        return builtQuery;

    }

}
