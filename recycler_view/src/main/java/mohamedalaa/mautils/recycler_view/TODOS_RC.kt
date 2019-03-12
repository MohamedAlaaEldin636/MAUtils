package mohamedalaa.mautils.recycler_view

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 3/11/2019.
 *
 * TODO
 *
 * 1- for linear layout manager isa, make isBorderTop as it is isa easy and there is no probs at all isa
 * and test it vertically and horizontally isa.
 *
 * 2- delete other unnecessary classes and files
 *
 * 3- put flavor for data binding isa
 *
 * 4- enhance rc adapter to name MA prefixing and getLayoutRes depending on position for
 * item type under the hood isa.
 *
 * 5- have item decor for grid but evenly so very left is half of middle isa.
 *
 * 6- keep old approach with warning if offset is too much the cell will not be eqactly same
 * and increase of offset makes difference pops more isa.
 * HOWEVER use latest approach of yDistance as it produces smallest diff possible isa
 * also do not make xDistance just all are yDistanceS isa so gaps are equal isa.
 * just add exact width of children as option like ignoreBorder w kda w dah elle feh
 * yDistance right and left fa el very left is half of middle one isa.
 * also hint use that it can be solved with padding of rc just last one of exact children isa.
 * and maybe just maybe for others but i do not think so, so check it isa.
 *
 * Future Notes Only sol isa, for grid
 * 1- have rc adapter under the hood to have 2 item types 1 of them is divider by .xml layout isa
 * 2- and surely grid layout manager under the hood to increase span count isa
 */