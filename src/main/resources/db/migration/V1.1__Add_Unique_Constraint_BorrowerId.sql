ALTER TABLE library.borrower
ADD CONSTRAINT unique_borrower_id
UNIQUE (borrower_id);
