CLONE:
https://github.com/ForrestTracy/Demo

APPLICANT TO COMPLETE:

1. Build a controller layer so that the following CURL returns an Invoice object
   $ curl --location 'http://localhost:8080/invoice/11111111-1111-1111-1111-111111111111'

2. Fix a bug where BillingRecords with the same id are being returned on a GET invoice call in the 'invoice.billingRecords' field
   For the invoice.id '11111111-1111-1111-1111-111111111112'

3. Getting 200 response when an Invoice or BillingRecord are not found in the DB.
   UI team is letting us know that is not the expectation. Please fix that.

4. Product wants the UI to be able to hit a GET endpoint (or endpoints) and get all or only get a subset of the User's Billing Records or Invoices.

5. Push all your changes to your own repo (not a branch on mine). Then email me a link to it at forrest.tracy@bestbuy.com