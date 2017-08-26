USE `bookstore`;
DROP TRIGGER IF EXISTS `check_left_books`;
DELIMITER $$ 
CREATE DEFINER = CURRENT_USER TRIGGER `bookstore`.`check_left_books` BEFORE UPDATE ON `book_stock`
FOR EACH ROW
BEGIN
IF NEW.Left_Num < 0 THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Remaining quantity can not be negative';
END IF; 
END$$ 
DELIMITER ;

/* ******************** */

USE `bookstore`;
DROP TRIGGER IF EXISTS `delete_order`;
DELIMITER $$ 
CREATE DEFINER = CURRENT_USER TRIGGER `bookstore`.`delete_order` BEFORE DELETE ON `needs_orders`
FOR EACH ROW
BEGIN
	UPDATE book_stock
	SET book_stock.Left_Num = Left_Num + OLD.Quantity
    WHERE OLD.ISBN = book_stock.ISBN AND OLD.Is_Need = 0;

END$$ 
DELIMITER ;

/*  ********************** */

USE `bookstore`;
DROP TRIGGER IF EXISTS `left_less_than_threshold`;
DELIMITER $$ 
CREATE DEFINER = CURRENT_USER TRIGGER `bookstore`.`left_less_than_threshold` AFTER UPDATE ON `book_stock`
FOR EACH ROW
BEGIN
	IF NEW.Left_Num < OLD.Threshold THEN
		IF EXISTS (SELECT * FROM needs_orders WHERE ISBN = NEW.ISBN) THEN
			update needs_orders set Quantity =  NEW.Threshold - NEW.Left_Num where ISBN = NEW.ISBN;
		ELSE
			INSERT INTO needs_orders VALUES (NEW.ISBN, NEW.Threshold - NEW.Left_Num, 1);
		END IF;
	END IF; 
END$$ 
DELIMITER ;