ALTER TABLE library.book_copy
RENAME COLUMN returned_date TO last_returned_date;

ALTER TABLE library.book_copy
RENAME COLUMN borrowed_date TO last_borrowed_date;