package com.ms.library.svc.constant;

public class ResourcePath {

  //Common
  public static final String VERSION_V1 = "/v1";

  //Borrower
  public static final String GET_BORROWER_BY_STATUS = "/get-borrower-list/{status}";
  public static final String REGISTER_BORROWER = "/register-borrower";


  //Book
  public static final String REGISTER_BOOK = "/register-book";
  public static final String GET_BOOK_PAGEABLE = "/get-book-list";
  public static final String BORROW_BOOK = "/borrow-book";
  public static final String RETURN_BOOK = "/return-book";

}
