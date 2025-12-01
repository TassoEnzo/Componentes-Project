DROP DATABASE IF EXISTS livraria;
DROP USER IF EXISTS 'livraria_user'@'localhost';

CREATE DATABASE livraria CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'livraria_user'@'localhost' IDENTIFIED BY 'livraria123';

GRANT ALL PRIVILEGES ON livraria.* TO 'livraria_user'@'localhost';
FLUSH PRIVILEGES;

USE livraria;

CREATE TABLE clientes (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NULL,
    telefone VARCHAR(20) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_email ON clientes(email);
CREATE INDEX idx_cpf ON clientes(cpf);

CREATE TABLE livros (
    id VARCHAR(36) PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    estoque INT NOT NULL DEFAULT 0,
    categoria VARCHAR(100),
    isbn VARCHAR(20),
    imagem_url VARCHAR(500),
    ano_publicacao INT NULL DEFAULT NULL,
    editora VARCHAR(255) NULL DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_categoria ON livros(categoria);
CREATE INDEX idx_autor ON livros(autor);
CREATE INDEX idx_isbn ON livros(isbn);

-- Livros de Programação
INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Clean Code', 'Robert C. Martin', 'Um clássico sobre código limpo e boas práticas de programação. Essencial para todo desenvolvedor que deseja escrever código de qualidade.', 129.90, 20, 'Programação', '9780132350884', 'https://picsum.photos/seed/cleancode/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Domain-Driven Design', 'Eric Evans', 'Base definitiva sobre DDD. Aprenda a modelar domínios complexos e criar software que reflete as necessidades do negócio.', 199.90, 15, 'Arquitetura', '9780321125217', 'https://picsum.photos/seed/ddd/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Refactoring', 'Martin Fowler', 'Melhores práticas para refatorar software. Aprenda a melhorar o design de código existente sem alterar seu comportamento.', 159.90, 10, 'Programação', '9780134757599', 'https://picsum.photos/seed/refactoring/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Clean Architecture', 'Robert C. Martin', 'Guia completo de arquitetura limpa. Aprenda a criar sistemas que são independentes de frameworks, testáveis e sustentáveis.', 149.90, 30, 'Arquitetura', '9780134494166', 'https://picsum.photos/seed/cleanarch/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Estruturas de Dados e Algoritmos em Java', 'Robert Lafore', 'Estruturas e algoritmos explicados de forma clara com exemplos práticos em Java. Ideal para iniciantes e intermediários.', 99.90, 40, 'Programação', '9788576082682', 'https://picsum.photos/seed/estruturas/300/450');

-- Livros de Ficção
INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), '1984', 'George Orwell', 'Romance distópico que retrata um futuro totalitário onde o Estado controla todos os aspectos da vida. Um clássico atemporal sobre vigilância e liberdade.', 35.90, 50, 'Ficção Científica', '9788535914849', 'https://picsum.photos/seed/1984/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'O Senhor dos Anéis: A Sociedade do Anel', 'J.R.R. Tolkien', 'Primeira parte da épica trilogia. Acompanhe Frodo e seus companheiros em uma jornada para destruir o Um Anel e salvar a Terra Média.', 54.90, 25, 'Fantasia', '9788533613379', 'https://picsum.photos/seed/lotr/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Harry Potter e a Pedra Filosofal', 'J.K. Rowling', 'O início da saga mágica mais famosa do mundo. Descubra o mundo mágico através dos olhos de Harry Potter em seu primeiro ano em Hogwarts.', 39.90, 60, 'Fantasia', '9788532530787', 'https://picsum.photos/seed/harrypotter/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'O Hobbit', 'J.R.R. Tolkien', 'A aventura que deu origem à saga do Senhor dos Anéis. Bilbo Bolseiro embarca em uma jornada inesperada que mudará sua vida para sempre.', 44.90, 35, 'Fantasia', '9788595084742', 'https://picsum.photos/seed/hobbit/300/450');

-- Literatura Brasileira
INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Dom Casmurro', 'Machado de Assis', 'Clássico da literatura brasileira que narra a história de Bentinho e sua desconfiança sobre a fidelidade de Capitu. Um dos maiores romances da língua portuguesa.', 29.90, 45, 'Literatura Brasileira', '9788544001219', 'https://picsum.photos/seed/domcasmurro/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Grande Sertão: Veredas', 'Guimarães Rosa', 'Obra-prima da literatura brasileira. Riobaldo narra sua vida de jagunço e seu amor por Diadorim, em uma linguagem única e poética.', 54.90, 20, 'Literatura Brasileira', '9788535908770', 'https://picsum.photos/seed/grandesertao/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Memórias Póstumas de Brás Cubas', 'Machado de Assis', 'Romance revolucionário narrado por um defunto autor. Brás Cubas conta sua vida de forma irônica e filosófica, criando um marco na literatura brasileira.', 32.90, 38, 'Literatura Brasileira', '9788544001073', 'https://picsum.photos/seed/memoriaspostumas/300/450');

-- Desenvolvimento Pessoal
INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Hábitos Atômicos', 'James Clear', 'Descubra como pequenas mudanças podem gerar resultados extraordinários. Um guia prático para construir bons hábitos e quebrar maus hábitos.', 44.90, 70, 'Desenvolvimento Pessoal', '9788550807904', 'https://picsum.photos/seed/habitosatomicos/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'O Poder do Hábito', 'Charles Duhigg', 'Por que fazemos o que fazemos na vida e nos negócios. Entenda a ciência por trás dos hábitos e aprenda a transformá-los.', 39.90, 55, 'Desenvolvimento Pessoal', '9788539004119', 'https://picsum.photos/seed/poderhabito/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Mindset: A Nova Psicologia do Sucesso', 'Carol S. Dweck', 'Descubra como a mentalidade de crescimento pode transformar sua vida. Aprenda a abraçar desafios e desenvolver seu potencial máximo.', 42.90, 48, 'Desenvolvimento Pessoal', '9788547000240', 'https://picsum.photos/seed/mindset/300/450');

-- Negócios
INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'A Startup Enxuta', 'Eric Ries', 'Como os empreendedores de hoje utilizam inovação contínua para criar negócios de sucesso. Metodologia essencial para startups.', 49.90, 30, 'Negócios', '9788581780139', 'https://picsum.photos/seed/startupenxuta/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'De Zero a Um', 'Peter Thiel', 'O que aprender sobre empreendedorismo com o Vale do Silício. Insights únicos sobre como criar empresas que constroem o futuro.', 44.90, 35, 'Negócios', '9788547000059', 'https://picsum.photos/seed/zeroaum/300/450');

-- Ciência e História
INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Sapiens: Uma Breve História da Humanidade', 'Yuval Noah Harari', 'Da Idade da Pedra à era da inteligência artificial. Uma jornada fascinante pela história da espécie humana e seu impacto no mundo.', 54.90, 65, 'História', '9788525432629', 'https://picsum.photos/seed/sapiens/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'Uma Breve História do Tempo', 'Stephen Hawking', 'Do Big Bang aos Buracos Negros. O renomado físico explica os mistérios do universo de forma acessível e fascinante.', 39.90, 28, 'Ciência', '9788580576467', 'https://picsum.photos/seed/historiatempo/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'O Gene: Uma História Íntima', 'Siddhartha Mukherjee', 'A história da genética e seu impacto na medicina e na sociedade. Uma narrativa envolvente sobre a ciência que nos define.', 64.90, 22, 'Ciência', '9788535928358', 'https://picsum.photos/seed/gene/300/450');

-- Suspense e Mistério
INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'O Código Da Vinci', 'Dan Brown', 'Um thriller eletrizante que mistura arte, história e conspiração. Robert Langdon precisa decifrar códigos antigos para resolver um assassinato.', 44.90, 42, 'Suspense', '9788580411706', 'https://picsum.photos/seed/codigodavinci/300/450');

INSERT INTO livros (id, titulo, autor, descricao, preco, estoque, categoria, isbn, imagem_url) 
VALUES (UUID(), 'A Garota no Trem', 'Paula Hawkins', 'Suspense psicológico envolvente. Rachel observa um casal pela janela do trem todos os dias, até que presencia algo chocante.', 39.90, 36, 'Suspense', '9788501104403', 'https://picsum.photos/seed/garotatrem/300/450');