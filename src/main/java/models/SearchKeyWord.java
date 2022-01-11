package models;

import com.opencsv.bean.CsvBindByName;

public class SearchKeyWord{
    @CsvBindByName(column = "KeyWord")
    public String keyWord;

}
