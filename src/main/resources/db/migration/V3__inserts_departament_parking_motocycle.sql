-- Inserts para a tabela 'department'
-- ID 1: Logística
-- ID 2: Manutenção
-- ID 3: Administrativo
INSERT INTO department (id, name, description) VALUES
                                                   (1, 'Logística', 'Responsável pela frota de entrega e coleta.'),
                                                   (2, 'Manutenção', 'Responsável por reparos e revisões da frota.'),
                                                   (3, 'Sucata', 'Responsável por motos sucateadas');


INSERT INTO parking_space (parking_space_id, code, occupied, department_id) VALUES
(1, 'L-01', TRUE, 1),   -- Ocupada pela moto de ID 1
(2, 'L-02', TRUE, 1),   -- Ocupada pela moto de ID 2
(3, 'L-03', TRUE, 1),   -- Ocupada pela moto de ID 3
(4, 'L-04', FALSE, 1),  -- Livre
(5, 'L-05', FALSE, 1),  -- Livre
(6, 'L-06', TRUE, 1),   -- Ocupada pela moto de ID 5
(7, 'L-07', TRUE, 1),   -- Ocupada pela moto de ID 6
(8, 'L-08', FALSE, 1),  -- Livre
(9, 'L-09', FALSE, 1),  -- Livre
(10, 'L-10', TRUE, 1),  -- Ocupada pela moto de ID 8
(11, 'M-01', TRUE, 2),  -- Ocupada pela moto de ID 9
(12, 'M-02', FALSE, 2), -- Livre
(13, 'M-03', FALSE, 2), -- Livre
(14, 'M-04', FALSE, 2), -- Livre
(15, 'M-05', FALSE, 2), -- Livre
(16, 'A-01', TRUE, 3),  -- Ocupada pela moto de ID 10
(17, 'A-02', FALSE, 3), -- Livre
(18, 'A-03', FALSE, 3), -- Livre
(19, 'A-04', FALSE, 3), -- Livre
(20, 'A-05', FALSE, 3);


-- Inserts para a tabela 'motorcycle'
-- Se 'parking_space_id' tem um valor, a moto está estacionada.
-- Se 'parking_space_id' é NULL, a moto não está estacionada.
INSERT INTO motorcycle (id_motorcycle, plate, model, color, parking_space_id) VALUES
                                                                                  (1, 'ABC1234', 'Honda Pop 110i', 'Branco', 1),
                                                                                  (2, 'DEF5678', 'Honda Biz 125', 'Vermelho', 2),
                                                                                  (3, 'GHI9012', 'Honda Cargo', 'Azul', 3),
                                                                                  (4, 'JKL3456', 'Honda CG 160 Fan', 'Preto', NULL), -- Moto em uso
                                                                                  (5, 'MNO7890', 'Yamaha Fazer 250', 'Amarelo', 6),
                                                                                  (6, 'PQR1234', 'Yamaha Lander', 'Verde', 7),
                                                                                  (7, 'STU5678', 'Honda CG 160 Fan', 'Preto', NULL), -- Moto em uso
                                                                                  (8, 'VWX9012', 'Honda Biz 125', 'Vermelho', 10),
                                                                                  (9, 'YZA3456', 'Honda Pop 110i', 'Branco', 11),
                                                                                  (10, 'BCD7890', 'Honda CG 160 Titan', 'Prata', 16);
