# Parcel

Permettre d'envoyer une commande
une commande est un ensemble d'objet du shop
un objet a un nom, un prix, un poids.
On permet d'envoyer via bpost de deux manière différentes. Soit en envoi standard dépendant du poids total, soit en envoi express dépendant du poids total et d'une majoration de 5€. On prévoit de rajouter mondial relay, hermes, ups, dpd. La plupart des prestataires ne propose que la livraison standard. Seul bpost permet l'express. Si d'autres implémentaient l'express, ils auraient leur propre règle de calcul du cout supplémentaire (pas toujours 5€). DPD est dépendant de la dimension du colis.

Parcel
    - weight

order
    - item list
    - total
    - shipping

shipping
    - cost (depends on parcel)
    - contractor (optional)

item
    - name
    - price
    - weight



