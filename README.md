# Task
Take Home Task

To import dependencies -> run mvn dependency:tree -U in terminal to import required dependency

To start application -> Run the [MsLibraryWebSvcApplication.java](src%2Fmain%2Fjava%2Fcom%2Fms%2Flibrary%2Fsvc%2FMsLibraryWebSvcApplication.java)

For API -> Import the included postman, api are already registered and ready to be user.


-> borrowerPageableByStatus
- To get list of available borrowers
- For path variable, can be either "A" or "D"
- Support pagination, can configure pageSize, pageNo, orderBy, sortBy

-> registerBorrower
- To register new borrowers in system
- body must contain value, null and empty string not accepted

-> registerBook
- To register new books in system
- body must contain value, null and empty string not accepted

-> borrowBook
- To borrow book from system
- body must contain value, null and empty string not accepted

-> returnBook
- To return book to system
- body must contain value, null and empty string not accepted

-> bookResponseDTOPageable
- To get list of available books
- Support pagination, can configure pageSize, pageNo, orderBy, sortBy
- Can configure filtering by bookCode, isbn, title, author