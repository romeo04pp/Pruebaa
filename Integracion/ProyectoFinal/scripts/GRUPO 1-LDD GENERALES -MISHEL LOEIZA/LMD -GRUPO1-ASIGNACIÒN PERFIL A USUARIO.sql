-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-04-2025 a las 03:12:23
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sig2k25`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relperfusu`
--

CREATE TABLE `relperfusu` (
  `id_usuario` int(11) NOT NULL,
  `id_perfil` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `relperfusu`
--

INSERT INTO `relperfusu` (`id_usuario`, `id_perfil`) VALUES
(2026, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `relperfusu`
--
ALTER TABLE `relperfusu`
  ADD PRIMARY KEY (`id_usuario`,`id_perfil`),
  ADD KEY `id_perfil` (`id_perfil`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `relperfusu`
--
ALTER TABLE `relperfusu`
  ADD CONSTRAINT `relperfusu_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `relperfusu_ibfk_2` FOREIGN KEY (`id_perfil`) REFERENCES `perfiles2` (`id_perfil`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;