CREATE TABLE person
(
person_id bigint NOT NULL,
name character varying(99) NOT NULL,
surname character varying(99) NOT NULL,
PRIMARY KEY (person_id)
);

CREATE TABLE request
(
request_id bigint NOT NULL,
policy_number bigint NOT NULL,
request_text VARCHAR(5000) NOT NULL,
request_type integer NOT NULL,
person_id bigint NOT NULL,
PRIMARY KEY (request_id)
);

ALTER TABLE request
  ADD CONSTRAINT fk_request_person FOREIGN KEY (person_id) REFERENCES person;