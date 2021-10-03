# My Personal Project

## What will the application do?
This application will do a number of things. 
There will be functions pertaining to the **business** part of the truck

These functions are : 
- set the business name 
- display the current menu
- get the price of any item
- sell an item 
- get the total sales 

As well as functions to aid in **truck operations** 

These functions are:
- Set and Get the trucks location
- Get the trucks gas level
- Fill up the gas tank
- get the next location
- move to the next location


## Who will use it
This app can be used by somebody operating a moving vehicle for the purpose of 
retail. This includes somebody operating a **food truck**, a **kiosk at the mall**, 
or **clothing trucks** like the type that you see at festivals or events. 
While this may serve other purposes, these would be examples of users that 
utilize the app to its full potential.

## Why is this project of interest to you
This project is of interest to me for a more personal reason. My best friend has a
dream to own a food truck one day and travel with it, and while he may be an 
excellent chef, he is not the most organized. While this project may not be 
the type of idea that would get used by many people, I believe that for the
people that *can* use it, they will be very glad to have it. 

If I can create an application to keep track of imformation such as what all the
menu items are and how much they cost, and where you are and where you plan on going,
this could keep someone very organized. I think it could be very convenient to have
these two, very different sets of functions, linked together in one place for 
someone that needs access to both. 

# User Stories
- As a user, I want to be able to create a new menu item and add it to my menu
- As a user, I want to be able to get a list of names of every item on my menu
- As a user, I want to be able to sell and restock a menu item
- As a user, I want to be able to get a list of all the names of the items
 currently in stock
 
- As a user, I want to be able to save my menu list to file
- As a user, I want to be able to be able to load my menu list from file

- As a user, I want to be able to get the name, location, and gas percentage of my truck
- As a user, I want to be able to fill my gas tank

-**Phase 4: Task 2 : I have made the MenuItem class robust. The method sellItem 
throws an OutOfStockException when selling an item that has 0 quantity**


# Phase 4 : Task 3
- refactor DoStuffWithItems to be two different classes
- class for selling items 
- class for restocking items
- refactor the menu methods to be in their own class