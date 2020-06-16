package com.xk.mall.view.widget;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import cn.addapp.pickers.picker.LinkagePicker;

/**
 * 年月选择器
 */
public class XkPicker extends LinkagePicker {
    private static List<String> listDataYear=new ArrayList<>();
    private static List<String> listMonth=new ArrayList<>();

    public static List<String> getListMonth() {
        return listMonth;
    }

    public static List<String> getListDataYear() {
        return listDataYear;
    }

    public XkPicker(Activity activity) {
        super(activity,new XkDataProvide());
    }

    @Override
    protected int[] getColumnWidths(boolean onlyTwoColumn) {
        return new int[]{WRAP_CONTENT, WRAP_CONTENT, 0};
    }

    public static class XkDataProvide implements DataProvider{
        private List<String> yearDatas=new ArrayList<>();
        public XkDataProvide(){
            yearDatas.add("2018年");
            yearDatas.add("2019年");
            yearDatas.add("2020年");
            yearDatas.add("2021年");
            yearDatas.add("2022年");
            yearDatas.add("2023年");
            yearDatas.add("2024年");
            listDataYear.addAll(yearDatas);
        }

        @Override
        public boolean isOnlyTwo() {
            return true;
        }

        @Override
        public List<String> provideFirstData() {
            return yearDatas;
        }

        @Override
        public List<String> provideSecondData(int i) {
            return parseData();
        }

        @Override
        public List<String> provideThirdData(int i, int i1) {
            return new ArrayList<>();
        }

        //第2列的数据
        public static  List<String> parseData(){
            List<String> months = new ArrayList<>();
            for(int i=0;i<12;i++){
                if(i<9){
                    months.add("0"+String.valueOf(i+1)+"月");
                }else{
                    months.add(String.valueOf(i+1)+"月");
                }
            }
            listMonth.addAll(months);
            return  months;
        }
    }
}
