package util;
/*
  User: admin
  Cur_date: 27.08.2022
  Cur_time: 21:20
*/

import javax.servlet.http.HttpServletRequest;

public class DefaultPaginationSettings {
    public static int[] paginationDefaultSetting(HttpServletRequest req){
        String currPage = req.getParameter("currentPage");
        String recPerPage = req.getParameter("recordsPerPage");

        int currentPage;
        int recordsPerPage;

        if (currPage == null){
            currentPage = 1;
            recordsPerPage = 5; //default amount of data on one page
        } else {
            currentPage = Integer.parseInt(currPage);
            recordsPerPage = Integer.parseInt(recPerPage);
        }

        return new int[]{currentPage, recordsPerPage};
    }
}