USE hotelforlostsouls;


-- Bed arrangements for each room type --

INSERT INTO arrangements (Kingbeds, Queenbeds, Doublebeds)
VALUES 
(0, 0, 2), 
(0, 2, 0), 
(0, 2, 0), 
(1, 0, 0), 
(2, 0, 0); 

-- Amenities for each room type (Roomenities???) --

INSERT INTO amenities (Couch, Refrigerator, CoffeeMaker, TV, MiniBar, Desk, HotTub)
VALUES 
(0, 1, 1, 1, 0, 1, 0),
(1, 1, 1, 1, 1, 1, 0),
(1, 1, 2, 1, 1, 2, 0),
(1, 1, 1, 2, 2, 2, 1),
(2, 1, 2, 2, 2, 2, 1);

-- Feature Packages --

INSERT INTO featurepkgs (Description, Price)
VALUES 
('Poolside', 10),
('Top floor', 30),
('Lounge level', 10),
('Beachside', 50),
('Villa', 100);


INSERT INTO roomtypes (TypeName, ArrangementID, AmenityID)
VALUES
('Standard', 1, 1),
('Premium', 2, 2),
('Comfort Plus', 2, 3),
('Queen Suite', 3, 4),
('King Suite', 4, 4),
('Soul''s Keep', 5, 5);

INSERT INTO roombaseprices (RoomTypeID, BasePrice)
VALUES
(1, 100),
(2, 120),
(3, 140),
(4, 180),
(5, 220),
(6, 250);

INSERT INTO services (SvcDesc, BasePrice)
VALUES
('Food Service', 0),
('Drink Service', 0),
('Wake-up Call', 0),
('Massage (30 Min)', 45),
('Massage (90 Min)', 90);

INSERT INTO chargetypes (ChargeDesc)
VALUES
('Daily Rate'),
('Service Charge');

INSERT INTO promotions (PromoPercDisc, PromoAmtDisc, PromoDesc)
VALUES
(0, 10, '$10 off one night'),
(100, 0, 'Complimentary'),
(20, 0, '%20 off massage'),
(0, 30, '$25 off dinner');





