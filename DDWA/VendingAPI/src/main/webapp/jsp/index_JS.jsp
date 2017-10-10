<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <title>Vending Machine Monster</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/mycss.css" rel="stylesheet">
</head>
<body>

<div id="overallContainer" class="container">


    <div class="row text-center">
        <div class="col-12">
            <hr/>
            <div class="row">
                <div class="tDiv"><img src="images/tube.png" id="tubeLeft"/></div>
                <div class="tDiv" id="titleText">
                    <h2 id="pageTitle" class="text-center">Vintage Tube Vending </h2>
                    Feed your desire...
                </div>
                <div class="tDiv"><img src="images/tube.png" id="tubeRight"/></div>
                <div style="clear: both"></div>
            </div>
            <hr/>


            <div id="serverError" class="alert alert-danger">
                <strong>Server error: </strong> Try again later...
            </div>

        </div>
    </div>


    <div id="contentArea" class="rounded">
        <div class="row text-center">
            <div id="itemAreaMainDiv" class="col-sm-9">

            </div>


            <div id="controlPanelMainDiv" class="col-sm-3 rounded">
                <div class="row text-center">
                    <form class="form-group text-center">
                        <div class="container vbshrink">

                            <label for="totalMoney" class="col-12 col-form-label title">
                                <span class="title">Welcome</span>
                            </label>

                            <div class="row">
                                <div class="col-12">
                                    <input id="totalMoney" class="rounded" type="text" readonly="readonly">
                                </div>
                            </div>

                            <div class="row">
                                <button type="button" class="moneyButton" id="addDollarButton">$1.00</button>
                                <button type="button" class="moneyButton" id="addQuarterButton">$0.25</button>
                            </div>
                            <div class="row">
                                <button type="button" class="moneyButton" id="addDimeButton">$0.10</button>
                                <button type="button" class="moneyButton" id="addNickelButton">$0.05</button>
                            </div>
                        </div>
                    </form>
                </div>
                <hr/>
                <div class="row text-center vtshrink">

                    <div class="container text-center">
                        <div class="row"><span class="title">Messages</span></div>
                        <div class="row text-center">
                            <div id="messages" class="col-8 text-center rounded">
                                <span id="msgTxt"></span>
                                <span id="blkTxt"></span>
                            </div>
                        </div>
                    </div>
                    <form class="form-group text-center">
                        <div class="container vbshrink">
                            <div class="row">
                                <div class="col-12">
                                    <label for="itemDisplay" class="itemTitle">Item: </label>
                                    <input id="itemDisplay" class="rounded" type="text" readonly="readonly">
                                    <input id="currentItem" type="text" style="display:none;">
                                    <button type="button" id="purchaseButton">Make Purchase</button>
                                </div>
                            </div>

                        </div>
                    </form>
                    <hr/>
                </div>
                <div class="row text-center vtshrink" style="padding-bottom: 10px">
                    <div class="container text-center">
                        <div class="row"><span class="title">Change</span></div>
                        <div class="row text-center">
                            <div id="changeDisplay" class="col-8 text-center rounded"></div>
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <button type="button" id="changeButton">Coin Return</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/popper.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/purchase_handler.js"></script>
<script src="js/money_handler.js"></script>

<script src="js/item_handler.js"></script>
<script src="js/home.js"></script>
</body>
</html>
