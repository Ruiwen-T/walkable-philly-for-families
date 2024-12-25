# philly-walkable-for-families
Megan Zhang and Raven (Ruiwen) Tang

See `user-manual.pdf` for a step-by-step rundown on how to use our project :)

We created an interactive terminal-based algorithm that generates a walkable or bikeable summer day itinerary for families with children in Philadelphia. Given a starting zip code, we start the user on a playstreet (streets that are closed to traffic for children to play on when school's out, and that distribute meals to schoolchildren) within that
zip code. Then, based on the user's preferences, we find a shortest path including all the types of landmarks that they'd like (they may choose to include a picnic site, a pool, and a playground). Each stop will not be more than 2 miles away from the previous one to ensure that the itinerary is walkable/bikeable. If the user would like, they can also ask to be given a list of bookstores within their starting zip code. We pulled from the various Parks/Recreation datasets from OpenDataPhilly for playstreet, picnic site, pool, and playground (longitude, latitude, address, zip code, and name), and we scraped the Philly Bookstore Map website for the 46 bookstores in the city and their addresses and zip codes.

Work Breakdown:
- POI (Point of Interest) class (Megan + Raven)
- Pair class (Megan)
- Quad class (Megan)
- Read CSVs to populate map of zip codes to nodes (Megan)
- Populate graph (adjacency list) with nodes and edge weights (Raven)
- Search algorithm (given zip code and preferences, generate a path) (Megan)
- User input/output (Raven)
- Web scraping bookstore site (Raven)
- Filter bookstore for zip codes (Raven)
- User manual (Megan + Raven)
- summary.txt (Megan + Raven)
