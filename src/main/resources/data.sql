-- 1. Kullanıcı ekle
INSERT INTO users ( name, surname, tckn, role, username, password)
VALUES 
('Ali', 'Yılmaz', '12345678901', 'CUSTOMER', 'ali.yilmaz', '$2a$10$/rSzRVZ21bHaCJj55vAKBu540LizlpJn6z7oTRrP3hfClUOtySbhO'), -- şifre: 1234
( 'Oktay', 'Memmedli', '23456789012', 'EMPLOYEE', 'oktay.memmedli', '$2a$10$/rSzRVZ21bHaCJj55vAKBu540LizlpJn6z7oTRrP3hfClUOtySbhO'); -- şifre: 1234

-- 2. Cüzdan ekle
INSERT INTO wallet ( customer_id, wallet_name, currency, active_for_shopping, active_for_withdraw, balance, usable_balance)
VALUES 
( 1, 'Ali1', 'TRY', true, true, 1000.00, 1000.00);

-- 3. Örnek işlem (deposit)
INSERT INTO transaction ( wallet_id, amount, type, opposite_party_type, opposite_party, status)
VALUES
( 1, 1000.00, 'DEPOSIT', 'IBAN', 'TR0001000200030004', 'APPROVED');
