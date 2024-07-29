DROP
    DATABASE IF EXISTS fwrp;

CREATE
    DATABASE fwrp;

USE
    fwrp;

-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: locations
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `provinces`
--

DROP TABLE IF EXISTS `provinces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provinces` (
                             `id` char(2) NOT NULL,
                             `province` varchar(50) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provinces`
--

LOCK TABLES `provinces` WRITE;
/*!40000 ALTER TABLE `provinces` DISABLE KEYS */;
INSERT INTO `provinces` VALUES ('AB','Alberta'),('BC','British Columbia'),('MB','Manitoba'),('NB','New Brunswick'),('NL','Newfoundland and Labrador'),('NS','Nova Scotia'),('NT','Northwest Territories'),('NU','Nunavut'),('ON','Ontario'),('PE','Prince Edward Island'),('QC','Quebec'),('SK','Saskatchewan'),('YT','Yukon');
/*!40000 ALTER TABLE `provinces` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-24 10:41:58



-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: locations
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cities` (
                          `city` varchar(120) NOT NULL,
                          `province_id` char(2) NOT NULL,
                          PRIMARY KEY (`city`,`province_id`),
                          KEY `province_id` (`province_id`),
                          CONSTRAINT `cities_ibfk_1` FOREIGN KEY (`province_id`) REFERENCES `provinces` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES ('Airdrie','AB'),('Alberta Beach','AB'),('Athabasca','AB'),('Banff','AB'),('Barrhead','AB'),('Bassano','AB'),('Beaumont','AB'),('Beaverlodge','AB'),('Bentley','AB'),('Black Diamond','AB'),('Blackfalds','AB'),('Bon Accord','AB'),('Bonnyville','AB'),('Bow Island','AB'),('Bowden','AB'),('Brooks','AB'),('Bruederheim','AB'),('Calgary','AB'),('Calmar','AB'),('Camrose','AB'),('Canmore','AB'),('Cardston','AB'),('Carstairs','AB'),('Chestermere','AB'),('Claresholm','AB'),('Coaldale','AB'),('Coalhurst','AB'),('Cochrane','AB'),('Cold Lake','AB'),('Crossfield','AB'),('Crowsnest Pass','AB'),('Devon','AB'),('Didsbury','AB'),('Drayton Valley','AB'),('Drumheller','AB'),('Duchess','AB'),('Eckville','AB'),('Edmonton','AB'),('Edson','AB'),('Elk Point','AB'),('Falher','AB'),('Fort Macleod','AB'),('Fort Saskatchewan','AB'),('Fox Creek','AB'),('Gibbons','AB'),('Grande Cache','AB'),('Grande Prairie','AB'),('Grimshaw','AB'),('Hanna','AB'),('High Level','AB'),('High Prairie','AB'),('High River','AB'),('Hinton','AB'),('Innisfail','AB'),('Irricana','AB'),('Jasper','AB'),('Lacombe','AB'),('Lamont','AB'),('Leduc','AB'),('Legal','AB'),('Lethbridge','AB'),('Lloydminster','AB'),('Magrath','AB'),('Manning','AB'),('Mayerthorpe','AB'),('Medicine Hat','AB'),('Millet','AB'),('Morinville','AB'),('Nanton','AB'),('Nobleford','AB'),('Okotoks','AB'),('Olds','AB'),('Onoway','AB'),('Oyen','AB'),('Peace River','AB'),('Penhold','AB'),('Picture Butte','AB'),('Pincher Creek','AB'),('Ponoka','AB'),('Provost','AB'),('Raymond','AB'),('Red Deer','AB'),('Redcliff','AB'),('Redwater','AB'),('Rimbey','AB'),('Rocky Mountain House','AB'),('Sexsmith','AB'),('Slave Lake','AB'),('Springbrook','AB'),('Spruce Grove','AB'),('St. Albert','AB'),('St. Paul','AB'),('Stettler','AB'),('Stony Plain','AB'),('Strathmore','AB'),('Sundre','AB'),('Swan Hills','AB'),('Sylvan Lake','AB'),('Taber','AB'),('Three Hills','AB'),('Tofield','AB'),('Trochu','AB'),('Turner Valley','AB'),('Two Hills','AB'),('Valleyview','AB'),('Vauxhall','AB'),('Vegreville','AB'),('Vermilion','AB'),('Viking','AB'),('Vulcan','AB'),('Wabasca','AB'),('Wainwright','AB'),('Wembley','AB'),('Westlock','AB'),('Wetaskiwin','AB'),('Whitecourt','AB'),('Wood Buffalo','AB'),('Abbotsford','BC'),('Anmore','BC'),('Armstrong','BC'),('Ashcroft','BC'),('Barrière','BC'),('Bowen Island','BC'),('Burnaby','BC'),('Burns Lake','BC'),('Campbell River','BC'),('Castlegar','BC'),('Cedar','BC'),('Central Saanich','BC'),('Chase','BC'),('Chetwynd','BC'),('Chilliwack','BC'),('Clearwater','BC'),('Coldstream','BC'),('Colwood','BC'),('Comox','BC'),('Coombs','BC'),('Coquitlam','BC'),('Courtenay','BC'),('Cowichan Bay','BC'),('Cranbrook','BC'),('Creston','BC'),('Cumberland','BC'),('Dawson Creek','BC'),('Delta','BC'),('Duncan','BC'),('Dunsmuir','BC'),('East Kelowna','BC'),('Elkford','BC'),('Ellison','BC'),('Enderby','BC'),('Errington','BC'),('Esquimalt','BC'),('Fernie','BC'),('Fort St. James','BC'),('Fort St. John','BC'),('Fruitvale','BC'),('Gibsons','BC'),('Gold River','BC'),('Golden','BC'),('Grand Forks','BC'),('Grindrod','BC'),('Harrison Hot Springs','BC'),('Highlands','BC'),('Hilliers','BC'),('Hope','BC'),('Houston','BC'),('Hudson Hope','BC'),('Invermere','BC'),('Kaleden','BC'),('Kamloops','BC'),('Kelowna','BC'),('Kent','BC'),('Keremeos','BC'),('Kimberley','BC'),('Kitimat','BC'),('Ladysmith','BC'),('Lake Country','BC'),('Lake Cowichan','BC'),('Lakeview','BC'),('Langford Station','BC'),('Langley','BC'),('Lillooet','BC'),('Lions Bay','BC'),('Logan Lake','BC'),('Lumby','BC'),('Mackenzie','BC'),('Maple Ridge','BC'),('Merritt','BC'),('Metchosin','BC'),('Mission','BC'),('Nakusp','BC'),('Nanaimo','BC'),('Naramata','BC'),('Nelson','BC'),('New Westminster','BC'),('Nisga\'a','BC'),('North Cowichan','BC'),('North Saanich','BC'),('North Vancouver','BC'),('Northern Rockies','BC'),('Oak Bay','BC'),('Oliver','BC'),('One Hundred Mile House','BC'),('Ootischenia','BC'),('Osoyoos','BC'),('Parksville','BC'),('Peachland','BC'),('Pemberton','BC'),('Penticton','BC'),('Pitt Meadows','BC'),('Popkum','BC'),('Port Alberni','BC'),('Port Coquitlam','BC'),('Port Hardy','BC'),('Port McNeill','BC'),('Port Moody','BC'),('Powell River','BC'),('Prince George','BC'),('Prince Rupert','BC'),('Princeton','BC'),('Qualicum Beach','BC'),('Quesnel','BC'),('Richmond','BC'),('Rossland','BC'),('Royston','BC'),('Saanich','BC'),('Salmo','BC'),('Salmon Arm','BC'),('Saltair','BC'),('Sechelt','BC'),('Sicamous','BC'),('Sidney','BC'),('Smithers','BC'),('Sooke','BC'),('Sorrento','BC'),('Spallumcheen','BC'),('Sparwood','BC'),('Squamish','BC'),('Summerland','BC'),('Surrey','BC'),('Taylor','BC'),('Telkwa','BC'),('Terrace','BC'),('Tofino','BC'),('Trail','BC'),('Tumbler Ridge','BC'),('Ucluelet','BC'),('Valemount','BC'),('Vancouver','BC'),('Vanderhoof','BC'),('Vernon','BC'),('Victoria','BC'),('View Royal','BC'),('Warfield','BC'),('West Vancouver','BC'),('Westbank','BC'),('Whistler','BC'),('White Rock','BC'),('Williams Lake','BC'),('Windermere','BC'),('Youbou','BC'),('Alonsa','MB'),('Altona','MB'),('Arborg','MB'),('Argyle','MB'),('Armstrong','MB'),('Beauséjour','MB'),('Bifrost-Riverton','MB'),('Blumenort','MB'),('Boissevain','MB'),('Brandon','MB'),('Brenda-Waskada','MB'),('Brokenhead','MB'),('Carberry','MB'),('Carman','MB'),('Cartier','MB'),('Cartwright-Roblin','MB'),('Clanwilliam-Erickson','MB'),('Coldwell','MB'),('Cornwallis','MB'),('Dauphin','MB'),('De Salaberry','MB'),('Deloraine-Winchester','MB'),('Dufferin','MB'),('East St. Paul','MB'),('Ellice-Archie','MB'),('Elton','MB'),('Emerson-Franklin','MB'),('Ethelbert','MB'),('Fisher','MB'),('Flin Flon (Part)','MB'),('Gilbert Plains','MB'),('Gillam','MB'),('Gimli','MB'),('Glenboro-South Cypress','MB'),('Glenella-Lansdowne','MB'),('Grahamdale','MB'),('Grand View','MB'),('Grassland','MB'),('Grey','MB'),('Grunthal','MB'),('Hamiota','MB'),('Hanover','MB'),('Harrison Park','MB'),('Headingley','MB'),('Hillsburg-Roblin-Shell River','MB'),('Kelsey','MB'),('Killarney - Turtle Mountain','MB'),('La Broquerie','MB'),('Lac du Bonnet','MB'),('Lakeshore','MB'),('Landmark','MB'),('Lorette','MB'),('Lorne','MB'),('Louise','MB'),('Macdonald','MB'),('McCreary','MB'),('Melita','MB'),('Minitonas-Bowsman','MB'),('Minnedosa','MB'),('Minto-Odanah','MB'),('Mitchell','MB'),('Montcalm','MB'),('Morden','MB'),('Morris','MB'),('Mossey River','MB'),('Neepawa','MB'),('Niverville','MB'),('Norfolk-Treherne','MB'),('North Cypress-Langford','MB'),('North Norfolk','MB'),('Oakland-Wawanesa','MB'),('Oakview','MB'),('Pembina','MB'),('Pinawa','MB'),('Piney','MB'),('Pipestone','MB'),('Portage La Prairie','MB'),('Powerview-Pine Falls','MB'),('Prairie Lakes','MB'),('Prairie View','MB'),('Reinland','MB'),('Reynolds','MB'),('Riding Mountain West','MB'),('Ritchot','MB'),('Riverdale','MB'),('Rockwood','MB'),('Roland','MB'),('Rosedale','MB'),('Rossburn','MB'),('Rosser','MB'),('Russell-Binscarth','MB'),('Selkirk','MB'),('Sifton','MB'),('Souris-Glenwood','MB'),('Springfield','MB'),('St-Pierre-Jolys','MB'),('St. Andrews','MB'),('St. Clements','MB'),('St. François Xavier','MB'),('St. Laurent','MB'),('Stanley','MB'),('Ste. Anne','MB'),('Ste. Rose','MB'),('Steinbach','MB'),('Stonewall','MB'),('Stuartburn','MB'),('Swan River','MB'),('Swan Valley West','MB'),('Taché','MB'),('Teulon','MB'),('The Pas','MB'),('Thompson','MB'),('Two Borders','MB'),('Victoria','MB'),('Virden','MB'),('Wallace-Woodworth','MB'),('Wasagamack','MB'),('West Interlake','MB'),('West St. Paul','MB'),('WestLake-Gladstone','MB'),('Whitehead','MB'),('Winkler','MB'),('Winnipeg','MB'),('Winnipeg Beach','MB'),('Woodlands','MB'),('Yellowhead','MB'),('Allardville','NB'),('Alnwick','NB'),('Atholville','NB'),('Balmoral','NB'),('Bas Caraquet','NB'),('Bathurst','NB'),('Beaubassin East / Beaubassin-est','NB'),('Belledune','NB'),('Beresford','NB'),('Bertrand','NB'),('Botsford','NB'),('Bright','NB'),('Brighton','NB'),('Buctouche','NB'),('Burton','NB'),('Campbellton','NB'),('Cap Pele','NB'),('Caraquet','NB'),('Cardwell','NB'),('Charlo','NB'),('Chipman','NB'),('Cocagne','NB'),('Coverdale','NB'),('Dalhousie','NB'),('Denmark','NB'),('Dieppe','NB'),('Dorchester','NB'),('Douglas','NB'),('Dundas','NB'),('Durham','NB'),('Edmundston','NB'),('Eel River Crossing','NB'),('Florenceville','NB'),('Fredericton','NB'),('Glenelg','NB'),('Gordon','NB'),('Grand Bay-Westfield','NB'),('Grand Falls','NB'),('Grand Manan','NB'),('Greenwich','NB'),('Hampton','NB'),('Hanwell','NB'),('Hardwicke','NB'),('Havelock','NB'),('Hillsborough','NB'),('Kedgwick','NB'),('Kent','NB'),('Kingsclear','NB'),('Kingston','NB'),('Lamèque','NB'),('Lincoln','NB'),('Manners Sutton','NB'),('Maugerville','NB'),('McAdam','NB'),('Memramcook','NB'),('Minto','NB'),('Miramichi','NB'),('Moncton','NB'),('Musquash','NB'),('Nauwigewauk','NB'),('Neguac','NB'),('New Bandon','NB'),('New Maryland','NB'),('Newcastle','NB'),('Noonan','NB'),('Northampton','NB'),('Northesk','NB'),('Norton','NB'),('Oromocto','NB'),('Paquetville','NB'),('Peel','NB'),('Pennfield Ridge','NB'),('Perth','NB'),('Petit Rocher','NB'),('Petitcodiac','NB'),('Plaster Rock','NB'),('Queensbury','NB'),('Quispamsis','NB'),('Richibucto','NB'),('Richmond','NB'),('Riverview','NB'),('Rogersville','NB'),('Rothesay','NB'),('Sackville','NB'),('Saint Andrews','NB'),('Saint David','NB'),('Saint George','NB'),('Saint James','NB'),('Saint John','NB'),('Saint Martins','NB'),('Saint Mary','NB'),('Saint Marys','NB'),('Saint Stephen','NB'),('Saint-André','NB'),('Saint-Antoine','NB'),('Saint-Charles','NB'),('Saint-Jacques','NB'),('Saint-Joseph','NB'),('Saint-Léonard','NB'),('Saint-Louis','NB'),('Saint-Quentin','NB'),('Salisbury','NB'),('Shediac','NB'),('Shippagan','NB'),('Shippegan','NB'),('Simonds','NB'),('Southampton','NB'),('Southesk','NB'),('Springfield','NB'),('St. George','NB'),('Studholm','NB'),('Sussex','NB'),('Sussex Corner','NB'),('Tracadie','NB'),('Upham','NB'),('Upper Miramichi','NB'),('Wakefield','NB'),('Weldford','NB'),('Wellington','NB'),('Westfield Beach','NB'),('Wicklow','NB'),('Wilmot','NB'),('Woodstock','NB'),('Baie Verte','NL'),('Bay Bulls','NL'),('Bay Roberts','NL'),('Bishops Falls','NL'),('Bonavista','NL'),('Botwood','NL'),('Burgeo','NL'),('Burin','NL'),('Carbonear','NL'),('Centreville-Wareham-Trinity','NL'),('Channel-Port aux Basques','NL'),('Clarenville','NL'),('Clarkes Beach','NL'),('Conception Bay South','NL'),('Corner Brook','NL'),('Deer Lake','NL'),('Dildo','NL'),('Flat Rock','NL'),('Fogo Island','NL'),('Fortune','NL'),('Gambo','NL'),('Gander','NL'),('Glovertown','NL'),('Goulds','NL'),('Grand Bank','NL'),('Grand Falls','NL'),('Happy Valley','NL'),('Harbour Breton','NL'),('Harbour Grace','NL'),('Harbour Main-Chapel\'s Cove-Lakeview','NL'),('Holyrood','NL'),('Humbermouth','NL'),('Irishtown-Summerside','NL'),('Kippens','NL'),('Labrador City','NL'),('Lewisporte','NL'),('Logy Bay-Middle Cove-Outer Cove','NL'),('Marystown','NL'),('Massey Drive','NL'),('Mount Pearl Park','NL'),('Nain','NL'),('New-Wes-Valley','NL'),('Paradise','NL'),('Pasadena','NL'),('Placentia','NL'),('Portugal Cove-St. Philip\'s','NL'),('Pouch Cove','NL'),('Spaniards Bay','NL'),('Springdale','NL'),('St. Alban\'s','NL'),('St. Anthony','NL'),('St. George\'s','NL'),('St. John\'s','NL'),('St. Lawrence','NL'),('Stephenville','NL'),('Stephenville Crossing','NL'),('Torbay','NL'),('Trinity Bay North','NL'),('Twillingate','NL'),('Upper Island Cove','NL'),('Victoria','NL'),('Wabana','NL'),('Wabush','NL'),('Witless Bay','NL'),('Amherst','NS'),('Antigonish','NS'),('Argyle','NS'),('Barrington','NS'),('Berwick','NS'),('Bridgewater','NS'),('Cape Breton','NS'),('Centreville','NS'),('Chester','NS'),('Church Point','NS'),('Digby','NS'),('Falmouth','NS'),('Halifax','NS'),('Inverness','NS'),('Kentville','NS'),('Lantz','NS'),('Lunenburg','NS'),('Mahone Bay','NS'),('Middleton','NS'),('New Glasgow','NS'),('Oxford','NS'),('Parrsboro','NS'),('Pictou','NS'),('Port Hawkesbury','NS'),('Queens','NS'),('Shelburne','NS'),('Stellarton','NS'),('Stewiacke','NS'),('Trenton','NS'),('Truro','NS'),('Wedgeport','NS'),('Westville','NS'),('Windsor','NS'),('Wolfville','NS'),('Yarmouth','NS'),('Behchokò','NT'),('Fort Simpson','NT'),('Fort Smith','NT'),('Hay River','NT'),('Inuvik','NT'),('Yellowknife','NT'),('Arviat','NU'),('Baker Lake','NU'),('Cambridge Bay','NU'),('Cape Dorset','NU'),('Clyde River','NU'),('Gjoa Haven','NU'),('Igloolik','NU'),('Iqaluit','NU'),('Kugluktuk','NU'),('Pangnirtung','NU'),('Pond Inlet','NU'),('Rankin Inlet','NU'),('Repulse Bay','NU'),('Taloyoak','NU'),('Addington Highlands','ON'),('Adelaide-Metcalfe','ON'),('Adjala-Tosorontio','ON'),('Admaston/Bromley','ON'),('Ajax','ON'),('Alfred and Plantagenet','ON'),('Algonquin Highlands','ON'),('Alnwick/Haldimand','ON'),('Alvinston','ON'),('Amaranth','ON'),('Amherstburg','ON'),('Armour','ON'),('Armstrong','ON'),('Arnprior','ON'),('Arran-Elderslie','ON'),('Ashfield-Colborne-Wawanosh','ON'),('Asphodel-Norwood','ON'),('Assiginack','ON'),('Athens','ON'),('Atikokan','ON'),('Augusta','ON'),('Aurora','ON'),('Aylmer','ON'),('Bancroft','ON'),('Barrie','ON'),('Bayfield','ON'),('Bayham','ON'),('Beachburg','ON'),('Beckwith','ON'),('Belleville','ON'),('Black River-Matheson','ON'),('Blandford-Blenheim','ON'),('Blind River','ON'),('Bluewater','ON'),('Bonfield','ON'),('Bonnechere Valley','ON'),('Bracebridge','ON'),('Bradford West Gwillimbury','ON'),('Brampton','ON'),('Brant','ON'),('Brantford','ON'),('Breslau','ON'),('Brighton','ON'),('Brock','ON'),('Brockton','ON'),('Brockville','ON'),('Brudenell, Lyndoch and Raglan','ON'),('Burford','ON'),('Burlington','ON'),('Caledon','ON'),('Callander','ON'),('Cambridge','ON'),('Carleton Place','ON'),('Carling','ON'),('Casselman','ON'),('Cavan Monaghan','ON'),('Central Elgin','ON'),('Central Frontenac','ON'),('Central Huron','ON'),('Central Manitoulin','ON'),('Centre Hastings','ON'),('Centre Wellington','ON'),('Champlain','ON'),('Chapleau','ON'),('Chatham','ON'),('Chatsworth','ON'),('Chisholm','ON'),('Claremont','ON'),('Clarence-Rockland','ON'),('Clarington','ON'),('Clearview','ON'),('Cobalt','ON'),('Cobourg','ON'),('Cochrane','ON'),('Collingwood','ON'),('Conestogo','ON'),('Cornwall','ON'),('Cramahe','ON'),('Dawn-Euphemia','ON'),('Deep River','ON'),('Deseronto','ON'),('Douro-Dummer','ON'),('Drummond/North Elmsley','ON'),('Dryden','ON'),('Dutton/Dunwich','ON'),('Dysart et al','ON'),('East Ferris','ON'),('East Garafraxa','ON'),('East Gwillimbury','ON'),('East Hawkesbury','ON'),('East Zorra-Tavistock','ON'),('Edwardsburgh/Cardinal','ON'),('Elizabethtown-Kitley','ON'),('Elliot Lake','ON'),('Emo','ON'),('Englehart','ON'),('Enniskillen','ON'),('Erin','ON'),('Espanola','ON'),('Essa','ON'),('Essex','ON'),('Faraday','ON'),('Fort Erie','ON'),('Fort Frances','ON'),('French River / Rivière des Français','ON'),('Front of Yonge','ON'),('Frontenac Islands','ON'),('Gananoque','ON'),('Georgian Bay','ON'),('Georgian Bluffs','ON'),('Georgina','ON'),('Goderich','ON'),('Grand Valley','ON'),('Gravenhurst','ON'),('Greater Madawaska','ON'),('Greater Napanee','ON'),('Greenstone','ON'),('Grey Highlands','ON'),('Grimsby','ON'),('Guelph','ON'),('Guelph/Eramosa','ON'),('Halton Hills','ON'),('Hamilton','ON'),('Hamilton Township','ON'),('Hanover','ON'),('Hastings Highlands','ON'),('Havelock-Belmont-Methuen','ON'),('Hawkesbury','ON'),('Hearst','ON'),('Hensall','ON'),('Highlands East','ON'),('Hillsburgh','ON'),('Hindon Hill','ON'),('Horton','ON'),('Howick','ON'),('Huntsville','ON'),('Huron East','ON'),('Huron Shores','ON'),('Huron-Kinloss','ON'),('Ignace','ON'),('Ingersoll','ON'),('Innisfil','ON'),('Iroquois Falls','ON'),('Kapuskasing','ON'),('Kawartha Lakes','ON'),('Kenora','ON'),('Killaloe, Hagarty and Richards','ON'),('Kincardine','ON'),('King','ON'),('Kingsville','ON'),('Kirkland Lake','ON'),('Kitchener','ON'),('Komoka','ON'),('Laird','ON'),('Lake of Bays','ON'),('Lakeshore','ON'),('Lambton Shores','ON'),('Lanark Highlands','ON'),('Lappe','ON'),('LaSalle','ON'),('Laurentian Hills','ON'),('Laurentian Valley','ON'),('Leamington','ON'),('Leeds and the Thousand Islands','ON'),('Lincoln','ON'),('London','ON'),('Loyalist','ON'),('Lucan Biddulph','ON'),('Macdonald, Meredith and Aberdeen Additional','ON'),('Madawaska Valley','ON'),('Madoc','ON'),('Magnetawan','ON'),('Malahide','ON'),('Manitouwadge','ON'),('Mapleton','ON'),('Marathon','ON'),('Markham','ON'),('Markstay','ON'),('Marmora and Lake','ON'),('Mattawa','ON'),('McDougall','ON'),('McKellar','ON'),('McNab/Braeside','ON'),('Meaford','ON'),('Melancthon','ON'),('Merrickville','ON'),('Middlesex Centre','ON'),('Midland','ON'),('Milton','ON'),('Minto','ON'),('Mississauga','ON'),('Mississippi Mills','ON'),('Mono','ON'),('Montague','ON'),('Moonbeam','ON'),('Moosonee','ON'),('Morris-Turnberry','ON'),('Mulmur','ON'),('Muskoka Falls','ON'),('Neebing','ON'),('New Tecumseth','ON'),('Newmarket','ON'),('Niagara Falls','ON'),('Niagara-on-the-Lake','ON'),('Nipigon','ON'),('Nipissing','ON'),('North Algona Wilberforce','ON'),('North Bay','ON'),('North Dumfries','ON'),('North Dundas','ON'),('North Frontenac','ON'),('North Glengarry','ON'),('North Grenville','ON'),('North Huron','ON'),('North Kawartha','ON'),('North Middlesex','ON'),('North Perth','ON'),('North Stormont','ON'),('Northeastern Manitoulin and the Islands','ON'),('Northern Bruce Peninsula','ON'),('Norwich','ON'),('Oakville','ON'),('Oliver Paipoonge','ON'),('Orangeville','ON'),('Orillia','ON'),('Oro-Medonte','ON'),('Oshawa','ON'),('Otonabee-South Monaghan','ON'),('Ottawa','ON'),('Owen Sound','ON'),('Papineau-Cameron','ON'),('Parry Sound','ON'),('Pelham','ON'),('Pembroke','ON'),('Penetanguishene','ON'),('Perry','ON'),('Perth','ON'),('Perth East','ON'),('Perth South','ON'),('Petawawa','ON'),('Peterborough','ON'),('Petrolia','ON'),('Pickering','ON'),('Plantagenet','ON'),('Plympton-Wyoming','ON'),('Point Edward','ON'),('Port Colborne','ON'),('Port Hope','ON'),('Powassan','ON'),('Prescott','ON'),('Prince','ON'),('Puslinch','ON'),('Quinte West','ON'),('Ramara','ON'),('Red Lake','ON'),('Renfrew','ON'),('Richmond Hill','ON'),('Rideau Lakes','ON'),('Russell','ON'),('Sables-Spanish Rivers','ON'),('Sagamok','ON'),('Sarnia','ON'),('Saugeen Shores','ON'),('Sault Ste. Marie','ON'),('Schreiber','ON'),('Scugog','ON'),('Seguin','ON'),('Selwyn','ON'),('Severn','ON'),('Shelburne','ON'),('Shuniah','ON'),('Sioux Lookout','ON'),('Sioux Narrows-Nestor Falls','ON'),('Smiths Falls','ON'),('Smooth Rock Falls','ON'),('South Algonquin','ON'),('South Bruce','ON'),('South Bruce Peninsula','ON'),('South Dundas','ON'),('South Frontenac','ON'),('South Glengarry','ON'),('South Huron','ON'),('South River','ON'),('South Stormont','ON'),('South-West Oxford','ON'),('Southgate','ON'),('Southwest Middlesex','ON'),('Southwold','ON'),('Springwater','ON'),('St. Catharines','ON'),('St. Clair','ON'),('St. Joseph','ON'),('St. Marys','ON'),('St. Thomas','ON'),('St.-Charles','ON'),('Stirling-Rawdon','ON'),('Stone Mills','ON'),('Stouffville','ON'),('Stratford','ON'),('Strathroy-Caradoc','ON'),('Strong','ON'),('Sudbury','ON'),('Tara','ON'),('Tay','ON'),('Tay Valley','ON'),('Tecumseh','ON'),('Temiskaming Shores','ON'),('Terrace Bay','ON'),('Thames Centre','ON'),('The Blue Mountains','ON'),('The Nation / La Nation','ON'),('Thessalon','ON'),('Thorold','ON'),('Thunder Bay','ON'),('Tillsonburg','ON'),('Timmins','ON'),('Tiny','ON'),('Toronto','ON'),('Trent Hills','ON'),('Trent Lakes','ON'),('Tweed','ON'),('Tyendinaga','ON'),('Uxbridge','ON'),('Vaughan','ON'),('Wainfleet','ON'),('Warwick','ON'),('Wasaga Beach','ON'),('Waterloo','ON'),('Wawa','ON'),('Welland','ON'),('Wellesley','ON'),('Wellington','ON'),('Wellington North','ON'),('West Elgin','ON'),('West Grey','ON'),('West Lincoln','ON'),('West Nipissing / Nipissing Ouest','ON'),('West Perth','ON'),('Whitby','ON'),('Whitewater Region','ON'),('Wilmot','ON'),('Windsor','ON'),('Woodstock','ON'),('Woolwich','ON'),('Zorra','ON'),('Alberton','PE'),('Charlottetown','PE'),('Cornwall','PE'),('Kensington','PE'),('Malpeque','PE'),('Miltonvale Park','PE'),('Montague','PE'),('Souris','PE'),('Stratford','PE'),('Summerside','PE'),('Acton Vale','QC'),('Adstock','QC'),('Akwesasne','QC'),('Albanel','QC'),('Alma','QC'),('Amherst','QC'),('Amos','QC'),('Amqui','QC'),('Amulet','QC'),('Asbestos','QC'),('Ascot Corner','QC'),('Austin','QC'),('Ayer’s Cliff','QC'),('Baie-Comeau','QC'),('Baie-d’Urfé','QC'),('Baie-du-Febvre','QC'),('Baie-Saint-Paul','QC'),('Barraute','QC'),('Beaconsfield','QC'),('Beauceville','QC'),('Beauharnois','QC'),('Beauport','QC'),('Beaupré','QC'),('Bécancour','QC'),('Bedford','QC'),('Beloeil','QC'),('Berthier-sur-Mer','QC'),('Berthierville','QC'),('Blainville','QC'),('Blanc-Sablon','QC'),('Bois-des-Filion','QC'),('Boisbriand','QC'),('Boischatel','QC'),('Bonaventure','QC'),('Boucherville','QC'),('Brébeuf','QC'),('Brigham','QC'),('Bristol','QC'),('Bromont','QC'),('Brossard','QC'),('Brownsburg','QC'),('Bury','QC'),('Candiac','QC'),('Cantley','QC'),('Cap Santé','QC'),('Cap-Chat','QC'),('Cap-Saint-Ignace','QC'),('Caplan','QC'),('Carignan','QC'),('Carleton-sur-Mer','QC'),('Causapscal','QC'),('Chambly','QC'),('Chambord','QC'),('Champlain','QC'),('Chandler','QC'),('Chapais','QC'),('Charlemagne','QC'),('Château-Richer','QC'),('Châteauguay','QC'),('Chertsey','QC'),('Chibougamau','QC'),('Chisasibi','QC'),('Chute-aux-Outardes','QC'),('Clarendon','QC'),('Clermont','QC'),('Cleveland','QC'),('Coaticook','QC'),('Coleraine','QC'),('Compton','QC'),('Contrecoeur','QC'),('Cookshire','QC'),('Côte-Saint-Luc','QC'),('Coteau-du-Lac','QC'),('Cowansville','QC'),('Crabtree','QC'),('Danville','QC'),('Dégelis','QC'),('Déléage','QC'),('Delson','QC'),('Desbiens','QC'),('Deschambault','QC'),('Deux-Montagnes','QC'),('Disraeli','QC'),('Dollard-des-Ormeaux','QC'),('Donnacona','QC'),('Dorval','QC'),('Drummondville','QC'),('Dudswell','QC'),('Dunham','QC'),('Durham-Sud','QC'),('East Angus','QC'),('East Broughton','QC'),('Eastman','QC'),('Eeyou Istchee Baie-James','QC'),('Farnham','QC'),('Ferme-Neuve','QC'),('Fermont','QC'),('Forestville','QC'),('Fort-Coulonge','QC'),('Fossambault-sur-le-Lac','QC'),('Frampton','QC'),('Franklin Centre','QC'),('Frelighsburg','QC'),('Frontenac','QC'),('Gaspé','QC'),('Gatineau','QC'),('Girardville','QC'),('Godmanchester','QC'),('Gore','QC'),('Gracefield','QC'),('Granby','QC'),('Grand-Remous','QC'),('Grande-Rivière','QC'),('Grande-Vallée','QC'),('Grenville','QC'),('Grenville-sur-la-Rouge','QC'),('Hampstead','QC'),('Hatley','QC'),('Havre-Saint-Pierre','QC'),('Hébertville','QC'),('Hemmingford','QC'),('Henryville','QC'),('Hérbertville','QC'),('Hérouxville','QC'),('Hinchinbrooke','QC'),('Hudson','QC'),('Huntingdon','QC'),('Inukjuak','QC'),('Joliette','QC'),('Kingsey Falls','QC'),('Kirkland','QC'),('Kuujjuaq','QC'),('L\'Ange-Gardien','QC'),('L\'Anse-Saint-Jean','QC'),('L\'Ascension-de-Notre-Seigneur','QC'),('L\'Isle-aux-Allumettes','QC'),('L\'Isle-aux-Coudres','QC'),('L’ Îsle-Verte','QC'),('L’Ancienne-Lorette','QC'),('L’Assomption','QC'),('L’Avenir','QC'),('L’Epiphanie','QC'),('L’Île-Perrot','QC'),('L’Islet-sur-Mer','QC'),('La Conception','QC'),('La Doré','QC'),('La Guadeloupe','QC'),('La Macaza','QC'),('La Malbaie','QC'),('La Minerve','QC'),('La Pêche','QC'),('La Pocatière','QC'),('La Prairie','QC'),('La Présentation','QC'),('La Sarre','QC'),('La Tuque','QC'),('Labelle','QC'),('Labrecque','QC'),('Lac-au-Saumon','QC'),('Lac-aux-Sables','QC'),('Lac-Bouchette','QC'),('Lac-Brome','QC'),('Lac-des-Écorces','QC'),('Lac-Drolet','QC'),('Lac-Etchemin','QC'),('Lac-Mégantic','QC'),('Lac-Nominingue','QC'),('Lac-Supérieur','QC'),('Lachute','QC'),('Lacolle','QC'),('Lambton','QC'),('Lanoraie','QC'),('Larouche','QC'),('Laurier-Station','QC'),('Laurierville','QC'),('Laval','QC'),('Lavaltrie','QC'),('Lebel-sur-Quévillon','QC'),('Léry','QC'),('Les Cèdres','QC'),('Les Coteaux','QC'),('Les Éboulements','QC'),('Les Escoumins','QC'),('Les Îles-de-la-Madeleine','QC'),('Les Méchins','QC'),('Lévis','QC'),('Listuguj','QC'),('Longue-Rive','QC'),('Longueuil','QC'),('Lorraine','QC'),('Lorrainville','QC'),('Louiseville','QC'),('Lyster','QC'),('Macamic','QC'),('Magog','QC'),('Malartic','QC'),('Maliotenam','QC'),('Mandeville','QC'),('Maniwaki','QC'),('Manouane','QC'),('Mansfield-et-Pontefract','QC'),('Maria','QC'),('Marieville','QC'),('Mascouche','QC'),('Mashteuiatsh','QC'),('Maskinongé','QC'),('Matagami','QC'),('Matane','QC'),('McMasterville','QC'),('Melbourne','QC'),('Mercier','QC'),('Messines','QC'),('Métabetchouan-Lac-à-la-Croix','QC'),('Mille-Isles','QC'),('Mirabel','QC'),('Mistassini','QC'),('Mistissini','QC'),('Mont-Carmel','QC'),('Mont-Joli','QC'),('Mont-Laurier','QC'),('Mont-Orford','QC'),('Mont-Royal','QC'),('Mont-Saint-Grégoire','QC'),('Mont-Saint-Hilaire','QC'),('Mont-Tremblant','QC'),('Montmagny','QC'),('Montréal','QC'),('Montréal-Est','QC'),('Montréal-Ouest','QC'),('Morin-Heights','QC'),('Nantes','QC'),('Napierville','QC'),('Neuville','QC'),('New Carlisle','QC'),('New Richmond','QC'),('Nicolet','QC'),('Normandin','QC'),('Notre-Dame-de-l\'Île-Perrot','QC'),('Notre-Dame-de-Lourdes','QC'),('Notre-Dame-des-Neiges','QC'),('Notre-Dame-des-Pins','QC'),('Notre-Dame-des-Prairies','QC'),('Notre-Dame-du-Bon-Conseil','QC'),('Notre-Dame-du-Laus','QC'),('Notre-Dame-du-Mont-Carmel','QC'),('Notre-Dame-du-Nord','QC'),('Notre-Dame-du-Portage','QC'),('Nouvelle','QC'),('Noyan','QC'),('Obedjiwan','QC'),('Oka','QC'),('Old Chelsea','QC'),('Ormstown','QC'),('Otterburn Park','QC'),('Palmarolle','QC'),('Papineauville','QC'),('Paspebiac','QC'),('Percé','QC'),('Pessamit','QC'),('Piedmont','QC'),('Pierreville','QC'),('Pincourt','QC'),('Plaisance','QC'),('Plessisville','QC'),('Pohénégamook','QC'),('Pointe-à-la-Croix','QC'),('Pointe-aux-Outardes','QC'),('Pointe-Calumet','QC'),('Pointe-Claire','QC'),('Pointe-des-Cascades','QC'),('Pointe-Lebel','QC'),('Pont-Rouge','QC'),('Pontiac','QC'),('Port-Cartier','QC'),('Port-Daniel--Gascons','QC'),('Portneuf','QC'),('Potton','QC'),('Povungnituk','QC'),('Prévost','QC'),('Price','QC'),('Princeville','QC'),('Quebec City','QC'),('Racine','QC'),('Ragueneau','QC'),('Rawdon','QC'),('Repentigny','QC'),('Richelieu','QC'),('Richmond','QC'),('Rigaud','QC'),('Rimouski','QC'),('Ripon','QC'),('Rivière-Beaudette','QC'),('Rivière-Blanche','QC'),('Rivière-Bleue','QC'),('Rivière-du-Loup','QC'),('Rivière-Héva','QC'),('Riviere-Ouelle','QC'),('Rivière-Rouge','QC'),('Roberval','QC'),('Rosemère','QC'),('Rougemont','QC'),('Rouyn-Noranda','QC'),('Roxton Falls','QC'),('Roxton Pond','QC'),('Roxton-Sud','QC'),('Sacré-Coeur-Saguenay','QC'),('Saguenay','QC'),('Saint-Adolphe-d\'Howard','QC'),('Saint-Agapit','QC'),('Saint-Aimé-des-Lacs','QC'),('Saint-Alban','QC'),('Saint-Albert','QC'),('Saint-Alexandre','QC'),('Saint-Alexandre-de-Kamouraska','QC'),('Saint-Alexis','QC'),('Saint-Alexis-des-Monts','QC'),('Saint-Alphonse-de-Granby','QC'),('Saint-Alphonse-Rodriguez','QC'),('Saint-Amable','QC'),('Saint-Ambroise','QC'),('Saint-Ambroise-de-Kildare','QC'),('Saint-Anaclet-de-Lessard','QC'),('Saint-André-Avellin','QC'),('Saint-André-d\'Argenteuil','QC'),('Saint-Anicet','QC'),('Saint-Anselme','QC'),('Saint-Antoine-de-Tilly','QC'),('Saint-Antoine-sur-Richelieu','QC'),('Saint-Antonin','QC'),('Saint-Apollinaire','QC'),('Saint-Armand','QC'),('Saint-Arsène','QC'),('Saint-Aubert','QC'),('Saint-Augustin-de-Desmaures','QC'),('Saint-Barnabé','QC'),('Saint-Barthélemy','QC'),('Saint-Basile','QC'),('Saint-Basile-le-Grand','QC'),('Saint-Benoît-Labre','QC'),('Saint-Bernard','QC'),('Saint-Bernard-de-Lacolle','QC'),('Saint-Blaise-sur-Richelieu','QC'),('Saint-Bonaventure','QC'),('Saint-Boniface','QC'),('Saint-Bruno','QC'),('Saint-Bruno-de-Guigues','QC'),('Saint-Bruno-de-Montarville','QC'),('Saint-Calixte','QC'),('Saint-Casimir','QC'),('Saint-Césaire','QC'),('Saint-Charles-Borromée','QC'),('Saint-Charles-de-Bellechasse','QC'),('Saint-Charles-sur-Richelieu','QC'),('Saint-Christophe-d\'Arthabaska','QC'),('Saint-Chrysostome','QC'),('Saint-Claude','QC'),('Saint-Clet','QC'),('Saint-Colomban','QC'),('Saint-Côme','QC'),('Saint-Côme--Linière','QC'),('Saint-Constant','QC'),('Saint-Cuthbert','QC'),('Saint-Cyprien','QC'),('Saint-Cyprien-de-Napierville','QC'),('Saint-Cyrille-de-Wendover','QC'),('Saint-Damase','QC'),('Saint-Damien','QC'),('Saint-Damien-de-Buckland','QC'),('Saint-David-de-Falardeau','QC'),('Saint-Denis','QC'),('Saint-Denis-de-Brompton','QC'),('Saint-Dominique','QC'),('Saint-Donat','QC'),('Saint-Édouard','QC'),('Saint-Édouard-de-Lotbinière','QC'),('Saint-Élie-de-Caxton','QC'),('Saint-Elzéar','QC'),('Saint-Éphrem-de-Beauce','QC'),('Saint-Esprit','QC'),('Saint-Étienne-des-Grès','QC'),('Saint-Eugène','QC'),('Saint-Eustache','QC'),('Saint-Fabien','QC'),('Saint-Faustin--Lac-Carré','QC'),('Saint-Félicien','QC'),('Saint-Félix-de-Kingsey','QC'),('Saint-Félix-de-Valois','QC'),('Saint-Ferdinand','QC'),('Saint-Ferréol-les-Neiges','QC'),('Saint-Flavien','QC'),('Saint-François-de-la-Rivière-du-Sud','QC'),('Saint-François-du-Lac','QC'),('Saint-François-Xavier-de-Brompton','QC'),('Saint-Frédéric','QC'),('Saint-Fulgence','QC'),('Saint-Gabriel','QC'),('Saint-Gabriel-de-Brandon','QC'),('Saint-Gabriel-de-Rimouski','QC'),('Saint-Gabriel-de-Valcartier','QC'),('Saint-Gédéon','QC'),('Saint-Gédéon-de-Beauce','QC'),('Saint-Georges-de-Cacouna','QC'),('Saint-Georges-de-Clarenceville','QC'),('Saint-Germain-de-Grantham','QC'),('Saint-Gervais','QC'),('Saint-Gilles','QC'),('Saint-Guillaume','QC'),('Saint-Henri','QC'),('Saint-Hilarion','QC'),('Saint-Hippolyte','QC'),('Saint-Honoré','QC'),('Saint-Honoré-de-Shenley','QC'),('Saint-Hubert-de-Rivière-du-Loup','QC'),('Saint-Hugues','QC'),('Saint-Hyacinthe','QC'),('Saint-Ignace-de-Loyola','QC'),('Saint-Isidore','QC'),('Saint-Jacques','QC'),('Saint-Jacques-le-Mineur','QC'),('Saint-Jean-Baptiste','QC'),('Saint-Jean-de-Dieu','QC'),('Saint-Jean-de-Matha','QC'),('Saint-Jean-Port-Joli','QC'),('Saint-Jean-sur-Richelieu','QC'),('Saint-Jérôme','QC'),('Saint-Joachim','QC'),('Saint-Joachim-de-Shefford','QC'),('Saint-Joseph-de-Beauce','QC'),('Saint-Joseph-de-Coleraine','QC'),('Saint-Joseph-de-Sorel','QC'),('Saint-Joseph-du-Lac','QC'),('Saint-Jude','QC'),('Saint-Justin','QC'),('Saint-Lambert','QC'),('Saint-Lambert-de-Lauzon','QC'),('Saint-Laurent-de-l\'Île-d\'Orléans','QC'),('Saint-Lazare','QC'),('Saint-Lazare-de-Bellechasse','QC'),('Saint-Léon-de-Standon','QC'),('Saint-Léonard-d\'Aston','QC'),('Saint-Léonard-de-Portneuf','QC'),('Saint-Liboire','QC'),('Saint-Liguori','QC'),('Saint-Lin--Laurentides','QC'),('Saint-Louis-de-Gonzague','QC'),('Saint-Louis-du-Ha! Ha!','QC'),('Saint-Lucien','QC'),('Saint-Ludger','QC'),('Saint-Majorique-de-Grantham','QC'),('Saint-Malachie','QC'),('Saint-Marc-des-Carrières','QC'),('Saint-Marc-sur-Richelieu','QC'),('Saint-Martin','QC'),('Saint-Mathias-sur-Richelieu','QC'),('Saint-Mathieu','QC'),('Saint-Mathieu-de-Beloeil','QC'),('Saint-Mathieu-du-Parc','QC'),('Saint-Maurice','QC'),('Saint-Maxime-du-Mont-Louis','QC'),('Saint-Michel','QC'),('Saint-Michel-de-Bellechasse','QC'),('Saint-Michel-des-Saints','QC'),('Saint-Michel-du-Squatec','QC'),('Saint-Modeste','QC'),('Saint-Narcisse','QC'),('Saint-Narcisse-de-Beaurivage','QC'),('Saint-Narcisse-de-Rimouski','QC'),('Saint-Nazaire','QC'),('Saint-Norbert','QC'),('Saint-Norbert-d\'Arthabaska','QC'),('Saint-Odilon-de-Cranbourne','QC'),('Saint-Ours','QC'),('Saint-Pacôme','QC'),('Saint-Pamphile','QC'),('Saint-Pascal','QC'),('Saint-Patrice-de-Beaurivage','QC'),('Saint-Patrice-de-Sherrington','QC'),('Saint-Paul','QC'),('Saint-Paul-d\'Abbotsford','QC'),('Saint-Paul-de-l\'Île-aux-Noix','QC'),('Saint-Paulin','QC'),('Saint-Philippe','QC'),('Saint-Pie','QC'),('Saint-Pierre-de-l\'Île-d\'Orléans','QC'),('Saint-Pierre-les-Becquets','QC'),('Saint-Placide','QC'),('Saint-Polycarpe','QC'),('Saint-Prime','QC'),('Saint-Prosper','QC'),('Saint-Raphaël','QC'),('Saint-Raymond','QC'),('Saint-Rémi','QC'),('Saint-René-de-Matane','QC'),('Saint-Robert','QC'),('Saint-Roch-de-l\'Achigan','QC'),('Saint-Roch-de-Richelieu','QC'),('Saint-Sauveur','QC'),('Saint-Siméon','QC'),('Saint-Simon','QC'),('Saint-Stanislas','QC'),('Saint-Stanislas-de-Kostka','QC'),('Saint-Sulpice','QC'),('Saint-Sylvestre','QC'),('Saint-Théodore-d\'Acton','QC'),('Saint-Thomas','QC'),('Saint-Tite','QC'),('Saint-Tite-des-Caps','QC'),('Saint-Ubalde','QC'),('Saint-Urbain','QC'),('Saint-Urbain-Premier','QC'),('Saint-Valère','QC'),('Saint-Valérien-de-Milton','QC'),('Saint-Vallier','QC'),('Saint-Victor','QC'),('Saint-Wenceslas','QC'),('Saint-Zacharie','QC'),('Saint-Zénon','QC'),('Saint-Zotique','QC'),('Sainte-Adèle','QC'),('Sainte-Agathe-de-Lotbinière','QC'),('Sainte-Agathe-des-Monts','QC'),('Sainte-Angèle-de-Monnoir','QC'),('Sainte-Anne-de-Beaupré','QC'),('Sainte-Anne-de-Bellevue','QC'),('Sainte-Anne-de-la-Pérade','QC'),('Sainte-Anne-de-la-Pocatière','QC'),('Sainte-Anne-de-Sabrevois','QC'),('Sainte-Anne-de-Sorel','QC'),('Sainte-Anne-des-Lacs','QC'),('Sainte-Anne-des-Monts','QC'),('Sainte-Anne-des-Plaines','QC'),('Sainte-Anne-du-Sault','QC'),('Sainte-Barbe','QC'),('Sainte-Béatrix','QC'),('Sainte-Brigide-d\'Iberville','QC'),('Sainte-Brigitte-de-Laval','QC'),('Sainte-Catherine','QC'),('Sainte-Catherine-de-Hatley','QC'),('Sainte-Catherine-de-la-Jacques-Cartier','QC'),('Sainte-Cécile-de-Milton','QC'),('Sainte-Claire','QC'),('Sainte-Clotilde','QC'),('Sainte-Clotilde-de-Horton','QC'),('Sainte-Croix','QC'),('Sainte-Élisabeth','QC'),('Sainte-Émélie-de-l\'Énergie','QC'),('Sainte-Félicité','QC'),('Sainte-Geneviève-de-Batiscan','QC'),('Sainte-Geneviève-de-Berthier','QC'),('Sainte-Hélène-de-Bagot','QC'),('Sainte-Hénédine','QC'),('Sainte-Jeanne-d\'Arc','QC'),('Sainte-Julie','QC'),('Sainte-Julienne','QC'),('Sainte-Justine','QC'),('Sainte-Luce','QC'),('Sainte-Lucie-des-Laurentides','QC'),('Sainte-Madeleine','QC'),('Sainte-Marcelline-de-Kildare','QC'),('Sainte-Marguerite','QC'),('Sainte-Marguerite-du-Lac-Masson','QC'),('Sainte-Marie','QC'),('Sainte-Marie-Madeleine','QC'),('Sainte-Marie-Salomé','QC'),('Sainte-Marthe','QC'),('Sainte-Marthe-sur-le-Lac','QC'),('Sainte-Martine','QC'),('Sainte-Mélanie','QC'),('Sainte-Pétronille','QC'),('Sainte-Sabine','QC'),('Sainte-Sophie','QC'),('Sainte-Thècle','QC'),('Sainte-Thérèse','QC'),('Sainte-Thérèse-de-Gaspé','QC'),('Sainte-Ursule','QC'),('Sainte-Victoire-de-Sorel','QC'),('Saints-Anges','QC'),('Salaberry-de-Valleyfield','QC'),('Salluit','QC'),('Sayabec','QC'),('Scott','QC'),('Senneterre','QC'),('Sept-Îles','QC'),('Shannon','QC'),('Shawinigan','QC'),('Shawville','QC'),('Sherbrooke','QC'),('Sorel-Tracy','QC'),('Stanstead','QC'),('Stoke','QC'),('Stoneham-et-Tewkesbury','QC'),('Stukely-Sud','QC'),('Sutton','QC'),('Témiscaming','QC'),('Témiscouata-sur-le-Lac','QC'),('Terrasse-Vaudreuil','QC'),('Terrebonne','QC'),('Thetford Mines','QC'),('Thurso','QC'),('Tingwick','QC'),('Trécesson','QC'),('Très-Saint-Sacrement','QC'),('Tring-Jonction','QC'),('Trois-Pistoles','QC'),('Trois-Rivières','QC'),('Uashat','QC'),('Upton','QC'),('Val-d’Or','QC'),('Val-David','QC'),('Val-des-Monts','QC'),('Val-Joli','QC'),('Val-Morin','QC'),('Val-Shefford','QC'),('Valcourt','QC'),('Vallée-Jonction','QC'),('Varennes','QC'),('Vaudreuil-Dorion','QC'),('Vaudreuil-sur-le-Lac','QC'),('Venise-en-Québec','QC'),('Verchères','QC'),('Victoriaville','QC'),('Ville-Marie','QC'),('Warwick','QC'),('Waskaganish','QC'),('Waswanipi','QC'),('Waterloo','QC'),('Waterville','QC'),('Weedon-Centre','QC'),('Wemindji','QC'),('Wemotaci','QC'),('Wendake','QC'),('Wentworth-Nord','QC'),('Westmount','QC'),('Wickham','QC'),('Windsor','QC'),('Wotton','QC'),('Yamachiche','QC'),('Yamaska','QC'),('Yamaska-Est','QC'),('Air Ronge','SK'),('Assiniboia','SK'),('Balgonie','SK'),('Battle River No. 438','SK'),('Battleford','SK'),('Beaver River','SK'),('Biggar','SK'),('Birch Hills','SK'),('Blucher','SK'),('Britannia No. 502','SK'),('Buckland No. 491','SK'),('Buffalo Narrows','SK'),('Canora','SK'),('Canwood No. 494','SK'),('Carlyle','SK'),('Carnduff','SK'),('Corman Park No. 344','SK'),('Creighton','SK'),('Dalmeny','SK'),('Davidson','SK'),('Delisle','SK'),('Duck Lake No. 463','SK'),('Dundurn No. 314','SK'),('Edenwold No. 158','SK'),('Esterhazy','SK'),('Estevan','SK'),('Estevan No. 5','SK'),('Eston','SK'),('Flin Flon','SK'),('Foam Lake','SK'),('Fort Qu’Appelle','SK'),('Frenchman Butte','SK'),('Gravelbourg','SK'),('Grenfell','SK'),('Hudson Bay','SK'),('Hudson Bay No. 394','SK'),('Humboldt','SK'),('Ile-à-la-Crosse','SK'),('Indian Head','SK'),('Kamsack','SK'),('Kerrobert','SK'),('Kindersley','SK'),('Kindersley No. 290','SK'),('Kipling','SK'),('La Loche','SK'),('La Ronge','SK'),('Laird No. 404','SK'),('Lajord No. 128','SK'),('Langenburg','SK'),('Langham','SK'),('Lanigan','SK'),('Lloydminster','SK'),('Longlaketon No. 219','SK'),('Lumsden','SK'),('Lumsden No. 189','SK'),('Macklin','SK'),('Maidstone','SK'),('Maple Creek','SK'),('Maple Creek No. 111','SK'),('Martensville','SK'),('Meadow Lake','SK'),('Meadow Lake No. 588','SK'),('Melfort','SK'),('Melville','SK'),('Mervin No. 499','SK'),('Moose Jaw','SK'),('Moose Jaw No. 161','SK'),('Moosomin','SK'),('Nipawin','SK'),('Nipawin No. 487','SK'),('North Battleford','SK'),('Orkney No. 244','SK'),('Osler','SK'),('Outlook','SK'),('Oxbow','SK'),('Pelican Narrows','SK'),('Pilot Butte','SK'),('Pinehouse','SK'),('Preeceville','SK'),('Prince Albert','SK'),('Prince Albert No. 461','SK'),('Rama','SK'),('Regina','SK'),('Regina Beach','SK'),('Rosetown','SK'),('Rosthern','SK'),('Rosthern No. 403','SK'),('Saskatoon','SK'),('Shaunavon','SK'),('Shellbrook','SK'),('Shellbrook No. 493','SK'),('South Qu\'Appelle No. 157','SK'),('Spiritwood No. 496','SK'),('St. Louis No. 431','SK'),('Swift Current','SK'),('Swift Current No. 137','SK'),('Tisdale','SK'),('Torch River No. 488','SK'),('Unity','SK'),('Vanscoy No. 345','SK'),('Wadena','SK'),('Waldheim','SK'),('Warman','SK'),('Watrous','SK'),('Weyburn','SK'),('Weyburn No. 67','SK'),('White City','SK'),('Wilkie','SK'),('Wilton No. 472','SK'),('Wynyard','SK'),('Yorkton','SK'),('Dawson','YT'),('Whitehorse','YT');
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-24 10:41:58


-- -----------------------------------------------------
-- Table Users
-- -----------------------------------------------------
CREATE TABLE Users
(
    id    INT                                         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(127)                                NOT NULL,
    email VARCHAR(255)                                NOT NULL UNIQUE,
    type  ENUM ('retailer', 'charitable', 'consumer') NOT NULL
);

-- -----------------------------------------------------
-- Table Subscriptions
-- -----------------------------------------------------
CREATE TABLE Subscriptions
(
    consumer_id INT                   NOT NULL PRIMARY KEY,
    city        VARCHAR(120)          NOT NULL,
    province    CHAR(2)               NOT NULL,
    method      ENUM ('email', 'sms') NOT NULL,
    email       VARCHAR(255)          NULL,
    phone       VARCHAR(20)           NULL,
    status      BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (consumer_id) REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (city, province) REFERENCES cities (city, province_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table FoodInventory
-- -----------------------------------------------------
CREATE TABLE FoodInventory
(
    id             INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description    VARCHAR(255) NOT NULL,
    standard_price DOUBLE(6, 2) NOT NULL,
    quantity       INT(5)       NOT NULL,
    last_modified  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    average_rating DOUBLE(2, 1) NULL
);


-- -----------------------------------------------------
-- Table FoodItems
-- -----------------------------------------------------
CREATE TABLE FoodItems
(
    id                INT                                        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    expiration_date   DATETIME                                   NOT NULL,
    price             DOUBLE(6, 2)                               NULL,
    status            ENUM ('stock', 'donation', 'sale', 'sold') NOT NULL DEFAULT 'stock',
    retailer_id       INT                                        NOT NULL,
    food_inventory_id INT                                        NOT NULL,
    FOREIGN KEY (retailer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (food_inventory_id)
        REFERENCES FoodInventory (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table ClaimedFood
-- -----------------------------------------------------
CREATE TABLE ClaimedFood
(
    food_item_id  INT       NOT NULL,
    charitable_id INT       NOT NULL,
    claim_date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (food_item_id, charitable_id),
    FOREIGN KEY (food_item_id)
        REFERENCES FoodItems (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (charitable_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);
-- -----------------------------------------------------
-- Table PurchasedFood
-- -----------------------------------------------------
CREATE TABLE PurchasedFood
(
    food_item_id  INT       NOT NULL,
    consumer_id   INT       NOT NULL,
    purchase_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (food_item_id, consumer_id),
    FOREIGN KEY (food_item_id)
        REFERENCES FoodItems (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (consumer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table FoodPreferences
-- -----------------------------------------------------
CREATE TABLE FoodPreferences
(
    consumer_id       INT NOT NULL,
    food_inventory_id INT NOT NULL,
    PRIMARY KEY (consumer_id, food_inventory_id),
    FOREIGN KEY (consumer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (food_inventory_id)
        REFERENCES FoodInventory (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table Feedbacks
-- -----------------------------------------------------
CREATE TABLE Feedbacks
(
    consumer_id       INT      NOT NULL,
    food_inventory_id INT      NOT NULL,
    rating            INT(2)   NOT NULL,
    comment           TINYTEXT NOT NULL,
    PRIMARY KEY (consumer_id, food_inventory_id),
    FOREIGN KEY (consumer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (food_inventory_id)
        REFERENCES FoodInventory (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

INSERT INTO Users (name, email, type)
VALUES ('John Doe', 'john.doe@example.com', 'retailer'),
       ('Jane Smith', 'jane.smith@example.com', 'charitable'),
       ('Emily Johnson', 'emily.johnson@example.com', 'consumer'),
       ('Michael Brown', 'michael.brown@example.com', 'consumer'),
       ('Alice Green', 'alice.green@example.com', 'retailer'),
       ('Bob White', 'bob.white@example.com', 'charitable'),
       ('Catherine Lee', 'catherine.lee@example.com', 'consumer'),
       ('David Wilson', 'david.wilson@example.com', 'consumer'),
       ('Eve Black', 'eve.black@example.com', 'retailer'),
       ('Frank Harris', 'frank.harris@example.com', 'charitable'),
       ('Grace Adams', 'grace.adams@example.com', 'consumer'),
       ('Henry Lewis', 'henry.lewis@example.com', 'retailer'),
       ('Ivy Martinez', 'ivy.martinez@example.com', 'charitable'),
       ('Jack Robinson', 'jack.robinson@example.com', 'consumer'),
       ('Karen Walker', 'karen.walker@example.com', 'retailer'),
       ('Leo Harris', 'leo.harris@example.com', 'charitable'),
       ('Mia Scott', 'mia.scott@example.com', 'consumer'),
       ('Nathan Young', 'nathan.young@example.com', 'retailer'),
       ('Olivia King', 'olivia.king@example.com', 'charitable'),
       ('Paul Allen', 'paul.allen@example.com', 'consumer'),
       ('Quincy Wright', 'quincy.wright@example.com', 'retailer'),
       ('Rachel Hall', 'rachel.hall@example.com', 'charitable'),
       ('Sam Turner', 'sam.turner@example.com', 'consumer'),
       ('Tina Evans', 'tina.evans@example.com', 'retailer');


INSERT INTO Subscriptions (consumer_id, city, province, method, email, phone, status)
VALUES (1, 'Ottawa', 'ON', 'email', 'example1@example.com', NULL, TRUE),
       (2, 'Ottawa', 'ON', 'sms', NULL, '1234567890', FALSE),
       (3, 'Ottawa', 'ON', 'email', 'example2@example.com', NULL, TRUE),
       (4, 'Ottawa', 'ON', 'sms', NULL, '2345678901', TRUE),
       (5, 'Ottawa', 'ON', 'email', 'example3@example.com', NULL, FALSE),
       (6, 'Ottawa', 'ON', 'sms', NULL, '3456789012', TRUE),
       (7, 'Ottawa', 'ON', 'email', 'example4@example.com', NULL, TRUE),
       (8, 'Ottawa', 'ON', 'sms', NULL, '4567890123', FALSE),
       (9, 'Toronto', 'ON', 'email', 'example5@example.com', NULL, TRUE),
       (10, 'Montréal', 'QC', 'sms', NULL, '5678901234', FALSE),
       (11, 'Vancouver', 'BC', 'email', 'example6@example.com', NULL, TRUE),
       (12, 'Calgary', 'AB', 'sms', NULL, '6789012345', TRUE),
       (13, 'Edmonton', 'AB', 'email', 'example7@example.com', NULL, FALSE),
       (14, 'Winnipeg', 'MB', 'email', 'example8@example.com', NULL, TRUE),
       (15, 'Quebec City', 'QC', 'sms', NULL, '7890123456', TRUE),
       (16, 'Hamilton', 'ON', 'email', 'example9@example.com', NULL, FALSE),
       (17, 'Mississauga', 'ON', 'sms', NULL, '8901234567', TRUE),
       (18, 'Brampton', 'ON', 'email', 'example10@example.com', NULL, TRUE),
       (19, 'Surrey', 'BC', 'sms', NULL, '9012345678', FALSE),
       (20, 'Kitchener', 'ON', 'email', 'example11@example.com', NULL, TRUE),
       (21, 'Halifax', 'NS', 'sms', NULL, '0123456789', TRUE),
       (22, 'Laval', 'QC', 'email', 'example12@example.com', NULL, FALSE),
       (23, 'London', 'ON', 'sms', NULL, '1234567890', TRUE),
       (24, 'Victoria', 'BC', 'email', 'example13@example.com', NULL, TRUE);

INSERT INTO FoodInventory (description, standard_price, quantity, average_rating)
VALUES ('Apples', 1.50, 100, 4.5),
       ('Bread', 2.00, 50, 4.2),
       ('Milk', 1.20, 30, 4.8),
       ('Eggs', 3.00, 20, 4.0),
       ('Cheese', 4.00, 60, 4.7),
       ('Yogurt', 1.80, 40, 4.3),
       ('Chicken Breast', 5.00, 25, 4.6),
       ('Spinach', 2.50, 70, 4.4),
       ('Orange Juice', 3.00, 45, 4.5),
       ('Pasta', 1.75, 80, 4.2),
       ('Tomatoes', 2.00, 90, 4.6),
       ('Potatoes', 1.00, 150, 4.4),
       ('Carrots', 1.50, 120, 4.5),
       ('Lettuce', 1.80, 110, 4.3),
       ('Chicken Wings', 6.00, 35, 4.7),
       ('Beef Steak', 7.50, 25, 4.8),
       ('Salmon', 8.00, 20, 4.9),
       ('Pork Chops', 5.50, 30, 4.4),
       ('Olive Oil', 4.00, 50, 4.6),
       ('Rice', 2.50, 100, 4.5),
       ('Beans', 1.80, 70, 4.3),
       ('Cereal', 3.00, 60, 4.4),
       ('Juice', 2.50, 75, 4.3),
       ('Frozen Vegetables', 2.00, 40, 4.2),
       ('Apples', 1.50, 100, 4.5);

INSERT INTO FoodItems (expiration_date, price, status, retailer_id, food_inventory_id)
VALUES ('2024-08-01 12:00:00', 1.50, 'stock', 1, 1),
       ('2024-07-30 12:00:00', 2.00, 'donation', 1, 2),
       ('2024-08-05 12:00:00', 1.20, 'sale', 1, 3),
       ('2024-08-10 12:00:00', 3.00, 'stock', 1, 4),
       ('2024-08-15 12:00:00', 4.00, 'stock', 1, 5),
       ('2024-07-29 12:00:00', 1.80, 'donation', 2, 6),
       ('2024-08-12 12:00:00', 5.00, 'sale', 2, 7),
       ('2024-08-20 12:00:00', 2.50, 'stock', 2, 8),
       ('2024-07-28 12:00:00', 3.00, 'sale', 3, 9),
       ('2024-08-25 12:00:00', 4.00, 'stock', 3, 10),
       ('2024-09-01 12:00:00', 1.75, 'stock', 3, 11),
       ('2024-09-10 12:00:00', 2.00, 'donation', 4, 12),
       ('2024-09-15 12:00:00', 1.50, 'sale', 4, 13),
       ('2024-08-30 12:00:00', 2.00, 'stock', 4, 14),
       ('2024-07-25 12:00:00', 3.00, 'sale', 5, 15),
       ('2024-08-05 12:00:00', 4.00, 'stock', 5, 16),
       ('2024-08-10 12:00:00', 5.00, 'sale', 5, 17),
       ('2024-09-01 12:00:00', 2.50, 'stock', 5, 18),
       ('2024-09-10 12:00:00', 1.80, 'donation', 6, 19),
       ('2024-09-15 12:00:00', 2.00, 'sale', 6, 20),
       ('2024-08-30 12:00:00', 1.75, 'stock', 6, 21),
       ('2024-08-20 12:00:00', 3.00, 'stock', 7, 22),
       ('2024-08-25 12:00:00', 4.00, 'donation', 7, 23),
       ('2024-09-05 12:00:00', 5.00, 'sale', 7, 24);

INSERT INTO ClaimedFood (food_item_id, charitable_id, claim_date)
VALUES (1, 2, '2024-07-28 12:00:00'),
       (2, 3, '2024-07-29 12:00:00'),
       (3, 4, '2024-07-30 12:00:00'),
       (4, 5, '2024-07-31 12:00:00'),
       (5, 6, '2024-08-01 12:00:00'),
       (6, 7, '2024-08-02 12:00:00'),
       (7, 8, '2024-08-03 12:00:00'),
       (8, 9, '2024-08-04 12:00:00'),
       (9, 10, '2024-08-05 12:00:00'),
       (10, 11, '2024-08-06 12:00:00'),
       (11, 12, '2024-08-07 12:00:00'),
       (12, 13, '2024-08-08 12:00:00'),
       (13, 14, '2024-08-09 12:00:00'),
       (14, 15, '2024-08-10 12:00:00'),
       (15, 16, '2024-08-11 12:00:00'),
       (16, 17, '2024-08-12 12:00:00'),
       (17, 18, '2024-08-13 12:00:00'),
       (18, 19, '2024-08-14 12:00:00'),
       (19, 20, '2024-08-15 12:00:00'),
       (20, 21, '2024-08-16 12:00:00'),
       (21, 22, '2024-08-17 12:00:00'),
       (22, 23, '2024-08-18 12:00:00'),
       (23, 24, '2024-08-19 12:00:00'),
       (24, 1, '2024-08-20 12:00:00');

INSERT INTO PurchasedFood (food_item_id, consumer_id, purchase_date)
VALUES (1, 3, '2024-07-28 12:00:00'),
       (2, 4, '2024-07-29 12:00:00'),
       (3, 5, '2024-07-30 12:00:00'),
       (4, 6, '2024-07-31 12:00:00'),
       (5, 7, '2024-08-01 12:00:00'),
       (6, 8, '2024-08-02 12:00:00'),
       (7, 9, '2024-08-03 12:00:00'),
       (8, 10, '2024-08-04 12:00:00'),
       (9, 11, '2024-08-05 12:00:00'),
       (10, 12, '2024-08-06 12:00:00'),
       (11, 13, '2024-08-07 12:00:00'),
       (12, 14, '2024-08-08 12:00:00'),
       (13, 15, '2024-08-09 12:00:00'),
       (14, 16, '2024-08-10 12:00:00'),
       (15, 17, '2024-08-11 12:00:00'),
       (16, 18, '2024-08-12 12:00:00'),
       (17, 19, '2024-08-13 12:00:00'),
       (18, 20, '2024-08-14 12:00:00'),
       (19, 21, '2024-08-15 12:00:00'),
       (20, 22, '2024-08-16 12:00:00'),
       (21, 23, '2024-08-17 12:00:00'),
       (22, 24, '2024-08-18 12:00:00'),
       (23, 1, '2024-08-19 12:00:00'),
       (24, 2, '2024-08-20 12:00:00');

INSERT INTO FoodPreferences (consumer_id, food_inventory_id)
VALUES (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (4, 9),
       (4, 10),
       (4, 11),
       (4, 12),
       (5, 13),
       (5, 14),
       (5, 15),
       (5, 16),
       (5, 17),
       (5, 18),
       (6, 19),
       (6, 20),
       (6, 21),
       (6, 22),
       (6, 23),
       (6, 24),
       (7, 1),
       (7, 2);

INSERT INTO Feedbacks (consumer_id, food_inventory_id, rating, comment)
VALUES (3, 1, 5, 'Excellent quality!'),
       (3, 2, 4, 'Good value for money.'),
       (3, 3, 5, 'Loved it!'),
       (3, 4, 3, 'It was okay.'),
       (3, 5, 4, 'Tasty and fresh.'),
       (3, 6, 4, 'Would buy again.'),
       (4, 7, 5, 'Fantastic taste!'),
       (4, 8, 4, 'Nice and fresh.'),
       (4, 9, 5, 'Highly recommended.'),
       (4, 10, 3, 'Average product.'),
       (4, 11, 4, 'Good overall.'),
       (4, 12, 4, 'Satisfied with the quality.'),
       (5, 13, 5, 'Excellent as always!'),
       (5, 14, 4, 'Great product.'),
       (5, 15, 5, 'Delicious!'),
       (5, 16, 3, 'It was okay.'),
       (5, 17, 4, 'Would buy again.'),
       (5, 18, 5, 'Loved it!'),
       (6, 19, 4, 'Good quality.'),
       (6, 20, 5, 'Very fresh.'),
       (6, 21, 4, 'Happy with the purchase.'),
       (6, 22, 3, 'Okay, but could be better.'),
       (6, 23, 5, 'Fantastic product.'),
       (6, 24, 4, 'Pretty good.'),
       (7, 1, 5, 'Great taste!'),
       (7, 2, 4, 'Would recommend.');
