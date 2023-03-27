SELECT *
FROM PortfolioProject.dbo.[covid-deaths]
ORDER BY 3, 4

--SELECT *
--FROM PortfolioProject.dbo.[covid-vaccinations]

SELECT location, date, total_cases, new_cases, total_deaths, population
FROM PortfolioProject.dbo.[covid-deaths]
ORDER BY 1, 2

--Data types need to be changed from nvarchar in order to perform aggregate functions

ALTER TABLE PortfolioProject.dbo.[covid-deaths]
ALTER COLUMN total_cases FLOAT

ALTER TABLE PortfolioProject.dbo.[covid-deaths]
ALTER COLUMN total_deaths FLOAT

--Total Deaths as a percent of Total Cases

SELECT location, MAX(total_deaths) AS total_deaths, MAX(total_cases) AS total_cases, (MAX(total_deaths)/NULLIF(MAX(total_cases), 0))*100 AS death_percent_per_case
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent is not null
--WHERE location like '%united states'
GROUP BY location
ORDER BY death_percent_per_case DESC

--Total Cases as a percent of Population

SELECT location, MAX(total_cases) AS total_cases, population, (MAX(total_cases)/NULLIF(MAX(population), 0))*100 AS infected_per_pop
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent is not null
--WHERE location like '%united states'
GROUP BY location, population
ORDER BY infected_per_pop DESC

--Total Deaths as a percent of Population

SELECT location, MAX(total_deaths) AS total_deaths, population, (MAX(total_deaths)/NULLIF(MAX(population), 0))*100 AS death_percent_per_pop
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent is not null
--WHERE location like '%united states'
GROUP BY location, population
ORDER BY death_percent_per_pop DESC

--Total Deaths descending

SELECT location, MAX(total_deaths) AS total_deaths
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent is not null
--WHERE location like '%united states'
GROUP BY location
ORDER BY total_deaths DESC

--Total Deaths by continent

SELECT location, MAX(total_deaths) AS total_deaths
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent is null AND location NOT LIKE '%income%'
--WHERE location like '%united states'
GROUP BY location
ORDER BY total_deaths DESC

--Total Deaths by Income

SELECT location, MAX(total_deaths) AS total_deaths, population
FROM PortfolioProject.dbo.[covid-deaths]
WHERE location LIKE '%income%'
--WHERE location like '%united states'
GROUP BY location, population
ORDER BY total_deaths DESC

--World Data

SELECT date, SUM(new_cases) AS 'Cases Per Day', SUM(new_deaths) AS 'Deaths Per Day', (SUM(total_deaths)/NULLIF(SUM(total_cases), 0))*100 AS death_percent_per_case
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent IS NOT NULL
GROUP BY date
ORDER BY date

SELECT SUM(new_cases) AS 'All Cases', SUM(new_deaths) AS 'All Deaths', (SUM(total_deaths)/NULLIF(SUM(total_cases), 0))*100 AS death_percent_per_case
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent IS NOT NULL


--Joining Tables for additional data

SELECT *
FROM PortfolioProject.dbo.[covid-deaths] dead
INNER JOIN PortfolioProject.dbo.[covid-vaccinations] vax ON dead.location = vax.location AND dead.date = vax.date

SELECT dead.location, MAX(dead.total_cases) AS 'Total Cases', MAX(dead.total_deaths) AS 'Total Deaths', MAX(vax.total_tests) AS 'Total Tests', MAX(vax.total_tests)/MAX(dead.total_deaths) AS 'Tests Per Death'
FROM PortfolioProject.dbo.[covid-deaths] dead
INNER JOIN PortfolioProject.dbo.[covid-vaccinations] vax ON dead.location = vax.location AND dead.date = vax.date
WHERE dead.continent IS NOT NULL AND tests_per_case IS NOT NULL
GROUP BY dead.location
ORDER BY [Tests Per Death] desc

ALTER TABLE PortfolioProject.dbo.[covid-vaccinations]
ALTER COLUMN new_vaccinations bigint


SELECT dead.continent, dead.location, dead.date, dead.population, dead.new_cases, dead.new_deaths, vax.new_vaccinations, SUM(vax.new_vaccinations) OVER (Partition BY dead.location ORDER BY dead.location, dead.date) AS 'Rolling Vax Total'
FROM PortfolioProject.dbo.[covid-deaths] dead
INNER JOIN PortfolioProject.dbo.[covid-vaccinations] vax ON dead.location = vax.location AND dead.date = vax.date
WHERE dead.continent IS NOT NULL
--GROUP BY dead.location, dead.date, dead.population, vax.total_tests
ORDER BY dead.location, dead.date

--CTE

WITH PopvsVax (continent, location, date, population, new_vaccinations, RollingPeopleVaccinated)
AS
(
SELECT dead.continent, dead.location, dead.date, dead.population, vax.new_vaccinations, SUM(CONVERT(bigint, vax.new_vaccinations)) OVER (Partition by dead.location ORDER BY dead.location, dead.date) AS RollingPeopleVaccinated
FROM PortfolioProject..[covid-deaths] dead
INNER JOIN PortfolioProject..[covid-vaccinations] vax
	ON dead.location = vax.location AND dead.date = vax.date
	WHERE dead.continent IS NOT NULL
	--ORDER BY 2, 3
)
SELECT location, (MAX(RollingPeopleVaccinated)/population)*100 AS Percent_Pop_Vaccinated
FROM PopvsVax
GROUP BY location, population
HAVING (MAX(RollingPeopleVaccinated)/population)*100 IS NOT NULL
ORDER BY 2 desc, 1

--Views

CREATE VIEW DeathToCases AS
SELECT location, MAX(total_deaths) AS total_deaths, MAX(total_cases) AS total_cases, (MAX(total_deaths)/NULLIF(MAX(total_cases), 0))*100 AS death_percent_per_case
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent is not null
--WHERE location like '%united states'
GROUP BY location
--ORDER BY death_percent_per_case DESC

CREATE VIEW CasesPerPop AS
SELECT location, MAX(total_cases) AS total_cases, population, (MAX(total_cases)/NULLIF(MAX(population), 0))*100 AS infected_per_pop
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent is not null
--WHERE location like '%united states'
GROUP BY location, population
--ORDER BY infected_per_pop DESC

CREATE VIEW DeathsByContinent AS
SELECT location, MAX(total_deaths) AS total_deaths
FROM PortfolioProject.dbo.[covid-deaths]
WHERE continent is null AND location NOT LIKE '%income%'
--WHERE location like '%united states'
GROUP BY location
--ORDER BY total_deaths DESC

CREATE VIEW DeathsByIncome AS
SELECT location, MAX(total_deaths) AS total_deaths, population
FROM PortfolioProject.dbo.[covid-deaths]
WHERE location LIKE '%income%'
--WHERE location like '%united states'
GROUP BY location, population
--ORDER BY total_deaths DESC