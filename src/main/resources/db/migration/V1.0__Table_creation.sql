

CREATE TABLE library.borrower (
    id uuid PRIMARY KEY,
    borrower_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    sts_cd varchar(50) NOT NULL,
    ver_nbr INT NULL,
    crt_by varchar(255) NULL,
    crt_time timestamptz NULL,
    upd_by varchar(255) NULL,
    upd_time timestamptz NULL
);

CREATE TABLE library.book (
    id uuid PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    sts_cd varchar(50) NOT NULL,
    ver_nbr INT NULL,
    crt_by varchar(255) NULL,
    crt_time timestamptz NULL,
    upd_by varchar(255) NULL,
    upd_time timestamptz NULL
);

CREATE TABLE library.book_copy (
    id uuid PRIMARY KEY,
    book_id UUID NOT NULL,
    FOREIGN KEY (book_id) REFERENCES library.book(id),
    borrowed_status VARCHAR(1) NULL,
    borrowed_date timestamptz NULL,
    returned_date timestamptz NULL,
    returning_due_date timestamptz NULL,
    borrower_id uuid NULL,
    sts_cd varchar(50) NOT NULL,
    ver_nbr INT NULL,
    crt_by varchar(255) NULL,
    crt_time timestamptz NULL,
    upd_by varchar(255) NULL,
    upd_time timestamptz NULL
);



