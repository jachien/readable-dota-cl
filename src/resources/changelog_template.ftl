<html>
<head>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=9">
    <link href="http://cdn.dota2.com/apps/dota2/css/static/reset.css?v=3886334" rel="stylesheet" type="text/css">
    <link href="http://www.dota2.com/public/css/global.css?v=b3886334" rel="stylesheet" type="text/css">
    <link href="http://www.dota2.com/public/css/global_english.css?v=BpCw4C6e29Gp" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery-1.7.1.min.js?v=3886334"></script>
    <link href="https://fonts.googleapis.com/css?family=Cinzel:400,700" rel="stylesheet">
    <title>Dota 2 - ${changelog.patchNumber?html} Changelog</title>

    <style>
        *, html, body, td {
            font-family: 'Radiance';
            color: #999999;
        }

        .outer {
            background-color: #0d0d0d;
            background-image: url(http://cdn.dota2.com/apps/dota2/images/bladeform_legacy/bg_06.jpg);
            background-repeat: repeat-y;
            background-position: top;
            overflow: auto;
        }

        .outer, .inner {
            margin-left: auto;
            margin-right: auto;
        }

        .inner {
            width: 947px;
            max-width: 90%
        }

        .inner h2 {
            background: -webkit-linear-gradient(left, #47688A, #29374C); /* For Safari 5.1 to 6.0 */
            background: -o-linear-gradient(right, #47688A, #29374C); /* For Opera 11.1 to 12.0 */
            background: -moz-linear-gradient(right, #47688A, #29374C); /* For Firefox 3.6 to 15 */
            background: linear-gradient(to right, #47688A, #29374C); /* Standard syntax */
            color: #FFFFFF;
            display: inline-block;
            text-transform: uppercase;
            font-size: 30px;
            letter-spacing: 1.5px;
            font-weight: 100;
            padding: 15px 30px 15px 30px;
        }

        .inner h3 {
            color: #4E739E;
            text-transform: uppercase;
            font-size: 24px;
            letter-spacing: 1px;
            margin-bottom: 20px;
        }

        .inner h4 {
            color: #FFFFFF;
            text-transform: uppercase;
            padding-top: 15px;
            font-size: 20px;
            letter-spacing: 1px;
            margin-bottom: 6px;
        }

        .inner #header {
            margin-top: 80px;
            margin-bottom: 40px;
        }

        .inner > div {
            margin-bottom: 20px;
        }

        .inner #heroes img {
            width: 118px;
            height: 67px;
        }

        figure {
            font-family: 'Radiance';
            color: #999999;
            font-size: 18px;
            line-height: 30px;
            display: block;
            width: auto;
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
            $( '#changelog #items figure img' ).each( function () {
                var strItemID = $( this ).attr( 'data-id' );
                $( this ).attr( 'src', 'http://cdn.dota2.com/apps/dota2/images/items/' + strItemID + '_lg.png' );
            });
            $( '#changelog #heroes figure img' ).each( function () {
                var strHeroID = $( this ).attr( 'data-id' );
                $( this ).attr( 'src', 'http://cdn.dota2.com/apps/dota2/images/heroes/' + strHeroID + '_full.png' );
            });
        });
    </script>
</head>

<body>
<div id="changelog" class="outer">
    <div class="inner">
        <div id="header">
            <h2>${changelog.patchNumber?html} Gameplay Update</h2>
        </div>

        <#if changelog.generalChanges?has_content>
        <div id="general">
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
        <div id="items">
            <h3>Item Changes</h3>
            <#list changelog.itemChanges as cl>
            <figure>
                <img data-id="${cl.name}" src="http://cdn.dota2.com/apps/dota2/images/items/${cl.name}_lg.png">
                <h4>${cl.localizedName?html}:</h4>
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
        <div id="heroes">
            <h3>Hero Changes</h3>
            <#list changelog.heroChanges as cl>
            <figure>
                <img data-id="${cl.name}" src="http://cdn.dota2.com/apps/dota2/images/heroes/${cl.name}_full.png">
                <h4>${cl.localizedName?html}:</h4>
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