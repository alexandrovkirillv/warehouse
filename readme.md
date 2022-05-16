Warehouse readme(https://warehouse212.herokuapp.com/)

Warehouse is app for storing products and giving report about it.

There are 3 possible api, which you can use:
1. add warehouse: POST https://warehouse212.herokuapp.com/warehouse/add
   Content-Type: application/json

{
"id": "1",
"name": "Main Warehouse"
}
2. add Product: {2} - is warehouseId
POST https://warehouse212.herokuapp.com/product/2/add
   Content-Type: application/json

{
"name": "Puppy Food",
"quality": 50,
"purchasePrice": 100,
"salePrice": 150,
"purchasePriceWithVAT": 150,
"salePriceWithVAT": 177
}

3. get report: GET https://warehouse212.herokuapp.com/report
