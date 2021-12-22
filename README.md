# ProductBarcodes
This is an app to help with products that are missing a barcode on the item, to allow them to easily be scanned. When the app is initially installed, there are no products in the database, but the more the user adds, the more they can use. This app was designed with best buy stores in mind, although it should still work with any other retail store.

# Code Info
This app was written in java, using android studio. The database uses `SQLite` and the barcode generator uses the `zxing` library.

# Usage
To begin, hit the `+` button. From there a menu will come up prompting the user to enter the name, SKU and console (default is none) of the product. Once products are added to the database, the user may click on any of the products in the list to display the barcode as well as an option to delete the product from the databse. 

# Screenshots

<img src="https://i3.lensdump.com/i/gh5JpH.jpg" width="194.4" height="437.4"> <img src="https://i2.lensdump.com/i/gh5C1x.jpg" width="194.4" height="437.4">
<img src="https://i.lensdump.com/i/gh5QF1.jpg" width="194.4" height="437.4"> <img src="https://i1.lensdump.com/i/gh5kxk.jpg" width="194.4" height="437.4">

# Authors Note
This is my first app so any feedback is greatly appreciated. I'm planning to add a couple features, for example, automatically adding the different versions of games based off the SKU (there seems to only be a change of one number in the string). Another feature I'd like to implement is importing/exporting databases such that if someone has a more complete list of products than someone else, they can share their data in order to prevent redundancy.
