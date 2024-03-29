CREATE TABLE IF NOT EXISTS user (
  id INT(11) NOT NULL AUTO_INCREMENT,
  password VARCHAR(20) NOT NULL,
  name VARCHAR(10) NOT NULL,
  email VARCHAR(45) NOT NULL,
  gender CHAR(1) NOT NULL,
  year INT(11) NOT NULL,
  disable CHAR(1) NOT NULL,
  point INT(11) NOT NULL,
  crt_date DATETIME NOT NULL,
  upd_date DATETIME NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8

CREATE TABLE IF NOT EXISTS pin (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  type VARCHAR(20) NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  address VARCHAR(500) NOT NULL,
  comment TEXT NOT NULL,
  state SMALLINT(1) NOT NULL DEFAULT '0',
  like INT(11) NOT NULL DEFAULT '0',
  user_id INT(11) NOT NULL,
  crt_date DATETIME NOT NULL,
  upd_date DATETIME NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT `$pin$user_id$user$id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8

CREATE TABLE IF NOT EXISTS `pin_image` (
  `pin_id` INT(10) UNSIGNED NOT NULL,
  `image_path` VARCHAR(225) NOT NULL,
  `state` SMALLINT(1) UNSIGNED NOT NULL DEFAULT '0',
  `crt_date` DATETIME NOT NULL,
  `upd_date` DATETIME NOT NULL,
  PRIMARY KEY (`pin_id`, `image_path`),
  CONSTRAINT `$pin_image$pin_id$pin$id`
    FOREIGN KEY (`pin_id`)
    REFERENCES `pin` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8