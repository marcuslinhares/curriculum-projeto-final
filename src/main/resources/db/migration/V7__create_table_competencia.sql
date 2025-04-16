CREATE TABLE "curriculum"."competencia" (
  "id" uuid PRIMARY KEY,
  "descricao" text NOT NULL,
  "nivel" varchar(55) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL,
  "curriculo_id" uuid NOT NULL
);

ALTER TABLE "curriculum"."competencia"
ALTER COLUMN "created_at" SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE "curriculum"."competencia"
ALTER COLUMN "updated_at" SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE "curriculum"."competencia" ADD FOREIGN KEY ("curriculo_id") REFERENCES "curriculum"."curriculo" ("id") ON DELETE CASCADE;