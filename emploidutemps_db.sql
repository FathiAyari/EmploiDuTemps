-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 26 déc. 2024 à 13:35
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `emploidutemps_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `tb_cours`
--

CREATE TABLE `tb_cours` (
  `id` int(11) NOT NULL,
  `classe` varchar(30) NOT NULL,
  `matiere` varchar(80) NOT NULL,
  `num_jour` smallint(6) DEFAULT NULL,
  `Jour` varchar(20) NOT NULL,
  `heure` varchar(20) NOT NULL,
  `matricule_ens` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `tb_cours`
--

INSERT INTO `tb_cours` (`id`, `classe`, `matiere`, `num_jour`, `Jour`, `heure`, `matricule_ens`) VALUES
(39, '1er', 'ANGLAIS', NULL, 'JEUDI', '4ème H', 'a'),
(40, '2eme', 'FRANÇAIS', NULL, 'MERCREDI', '4ème H', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `tb_enseignant`
--

CREATE TABLE `tb_enseignant` (
  `matricule` varchar(20) NOT NULL,
  `nom` varchar(150) NOT NULL,
  `contact` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `tb_enseignant`
--

INSERT INTO `tb_enseignant` (`matricule`, `nom`, `contact`) VALUES
('a', 'alex', 'test');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `tb_cours`
--
ALTER TABLE `tb_cours`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk` (`matricule_ens`);

--
-- Index pour la table `tb_enseignant`
--
ALTER TABLE `tb_enseignant`
  ADD PRIMARY KEY (`matricule`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `tb_cours`
--
ALTER TABLE `tb_cours`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `tb_cours`
--
ALTER TABLE `tb_cours`
  ADD CONSTRAINT `fk` FOREIGN KEY (`matricule_ens`) REFERENCES `tb_enseignant` (`matricule`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
