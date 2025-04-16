CREATE TABLE "curriculum"."curriculo" (
  "id" uuid PRIMARY KEY,
  "escolaridade" varchar(55) NOT NULL,
  "funcao" text NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL,
  "candidato_id" uuid NOT NULL
);

ALTER TABLE "curriculum"."curriculo"
ALTER COLUMN "created_at" SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE "curriculum"."curriculo"
ALTER COLUMN "updated_at" SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE "curriculum"."curriculo" ADD FOREIGN KEY ("candidato_id") REFERENCES "curriculum"."candidato" ("id") ON DELETE CASCADE;