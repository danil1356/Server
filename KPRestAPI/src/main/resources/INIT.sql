CREATE TABLE _language
(
"id" SERIAL PRIMARY KEY,
"name" TEXT
);


CREATE TABLE root_morphemes
(
"id" SERIAL PRIMARY KEY,
"id_language" INTEGER REFERENCES _language ("id"),
"name" TEXT DEFAULT ' '  NOT NULL,
"value" TEXT DEFAULT ' '  NOT NULL
);

CREATE TABLE affix_morphemes
(
"id" SERIAL PRIMARY KEY,
"id_language" INTEGER REFERENCES _language ("id"),
"name" TEXT DEFAULT ' '  NOT NULL
);

CREATE TABLE allomorphes
(
"id" SERIAL PRIMARY KEY,
"value" TEXT DEFAULT ' '  NOT NULL,
"id_affix" INTEGER REFERENCES affix_morphemes ("id")
);

CREATE TABLE concepts
(
"id" SERIAL PRIMARY KEY,
"name_eng" TEXT DEFAULT ' '  NOT NULL,
"name_ru" TEXT DEFAULT ' '  NOT NULL,
"giperonim" TEXT DEFAULT ' '  NOT NULL
);

CREATE TABLE morph_concepts
(
"id" SERIAL PRIMARY KEY,
"id_morph" INTEGER  REFERENCES root_morphemes ("id"),
"id_concepts" INTEGER REFERENCES concepts ("id")
);