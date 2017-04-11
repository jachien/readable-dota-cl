<html>
<head>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=9">
    <link href="http://cdn.dota2.com/apps/dota2/css/static/reset.css?v=3886334" rel="stylesheet" type="text/css">
    <link href="http://www.dota2.com/public/css/global.css?v=b3886334" rel="stylesheet" type="text/css">
    <link href="http://www.dota2.com/public/css/global_english.css?v=BpCw4C6e29Gp" rel="stylesheet" type="text/css">
    <#--<link href="http://www.dota2.com/public/css/bladeform_legacy.css?v=b3886334&amp;l=english" rel="stylesheet" type="text/css">-->
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery-1.7.1.min.js?v=3886334"></script>
    <script type="text/javascript">$J = jQuery;</script>
    <script type="text/javascript" src="http://steamcommunity-a.akamaihd.net/public/shared/javascript/shared_global.js?v=mpbggNaqEKRe&amp;l=english"></script>
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery.cycle.all.min.js?v=3886334"></script>
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery.easing.1.3.min.js?v=3886334"></script>
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery.imagesloaded.min.js?v=3886334"></script>
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/spin.min.js?v=3886334"></script><style></style>
    <link href="https://fonts.googleapis.com/css?family=Cinzel:400,700" rel="stylesheet">
    <title>Dota 2 - ${changelog.patchNumber?html} Changelog</title>

    <style>
        *, html, body, td {
            font-family: 'Radiance';
            color: #999999;
        }

        #BG6 {
            background-color: #0d0d0d;
        }

        .Outer {
            background-image: url(http://cdn.dota2.com/apps/dota2/images/bladeform_legacy/bg_06.jpg);
            background-repeat: repeat-y;
            background-position: top;
            overflow: auto;
        }

        .Outer, .Inner {
            margin-left: auto;
            margin-right: auto;
        }

        .Inner {
            position: relative;
            width: 947px;
            margin-top: 100px;
        }

        #BG6 h1 {
            color: #FFFFFF;
            text-transform: uppercase;
            padding-top: 15px;
            font-size: 20px;
            letter-spacing: 1px;
            margin-bottom: 6px;
        }

        #BG6 h2 {
            background: linear-gradient(to right, #47688A, #29374C);
            color: #FFFFFF;
            display: inline;
            text-transform: uppercase;
            font-size: 30px;
            letter-spacing: 1.5px;
            font-weight: 100;
            padding: 12px 28px 12px 28px;
        }

        #BG6 h3 {
            color: #4E739E;
            text-transform: uppercase;
            font-size: 24px;
            letter-spacing: 1px;
            margin-bottom: 20px;
        }

        #BG6 #General, #BG6 #Items, #BG6 #Heroes {
            margin-top: 20px;
        }

        #BG6 #Heroes img {
            width: 118px;
            height: 67px;
        }

        figure {
            font-family: 'Radiance';
            color: #999999;
            font-size: 18px;
            line-height: 30px;
            display: block;
            width: 949px;
            background-color: #080808;
            padding: 22px 50px 2px 50px;
            margin: 0px 0px 2px 0px;
        }

        ul {
            font-family: 'Radiance';
            color: #999999;
            font-size: 18px;
            line-height: 30px;
            list-style-type: circle;
            padding-bottom: 20px;
        }
    </style>

    <script type="text/javascript">
        $( function () {
            $( '#BG6 #Items figure img' ).each( function () {
                var strItemID = $( this ).attr( 'data-id' );
                $( this ).attr( 'src', 'http://cdn.dota2.com/apps/dota2/images/items/' + strItemID + '_lg.png' );
            });
            $( '#BG6 #Heroes figure img' ).each( function () {
                var strHeroID = $( this ).attr( 'data-id' );
                $( this ).attr( 'src', 'http://cdn.dota2.com/apps/dota2/images/heroes/' + strHeroID + '_full.png' );
            });
        });
    </script>
</head>

<body>
<div id="BG6" class="Outer">
    <div class="Inner">
        <h2>${changelog.patchNumber?html} Gameplay Update</h2>

        <#if changelog.generalChanges?has_content>
        <div id="General">
            <h3>General Changes</h3>
            <figure>
                <#list changelog.generalChanges as cl>
                <ul>
                    <#list cl.changes as change>
                    <li>${change?html}</li>
                    </#list>
                </ul>
                </#list>
            </figure>
        </div>
        </#if>

        <#if changelog.itemChanges?has_content>
        <div id="Items">
            <h3>Item Changes</h3>
            <#list changelog.itemChanges as cl>
            <figure>
                <img data-id="${cl.name}" src="http://cdn.dota2.com/apps/dota2/images/items/${cl.name}_lg.png">
                <h1>${cl.localizedName?html}:</h1>
                <ul>
                    <#list cl.changes as change>
                        <li>${change?html}</li>
                    </#list>
                </ul>
            </figure>
            </#list>
        </div>
        </#if>

        <#if changelog.heroChanges?has_content>
        <div id="Heroes">
            <h3>Hero Changes</h3>
            <#list changelog.heroChanges as cl>
            <figure>
                <img data-id="${cl.name}" src="http://cdn.dota2.com/apps/dota2/images/heroes/${cl.name}_full.png">
                <h1>${cl.localizedName?html}:</h1>
                <ul>
                    <#list cl.changes as change>
                        <li>${change?html}</li>
                    </#list>
                </ul>
            </figure>
            </#list>
        </div>
        </#if>

    </div>
</div>
</body>
</html>