ALTER TABLE library.book_copy
ALTER COLUMN borrower_id TYPE VARCHAR USING borrower_id::VARCHAR;
