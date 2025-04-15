CREATE TABLE "curriculum"."candidato" (
  "id" uuid PRIMARY KEY,
  "cpf" varchar(11) UNIQUE NOT NULL,
  "data_nasc" date NOT NULL,
  "telefone" varchar(14) NOT NULL,
  "situacao" varchar(55) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL,
  "usuario_id" uuid UNIQUE NOT NULL
);

ALTER TABLE "curriculum"."candidato"
ALTER COLUMN "created_at" SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE "curriculum"."candidato"
ALTER COLUMN "updated_at" SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE "curriculum"."candidato" ADD FOREIGN KEY ("usuario_id") REFERENCES "curriculum"."usuario" ("id") ON DELETE CASCADE;
