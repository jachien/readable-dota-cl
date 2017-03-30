<html>
<head>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=9">
    <link href="http://cdn.dota2.com/apps/dota2/css/static/reset.css?v=3886334" rel="stylesheet" type="text/css">
    <link href="http://www.dota2.com/public/css/global.css?v=b3886334" rel="stylesheet" type="text/css">
    <link href="http://www.dota2.com/public/css/global_english.css?v=BpCw4C6e29Gp" rel="stylesheet" type="text/css">
    <link href="http://www.dota2.com/public/css/bladeform_legacy.css?v=b3886334&amp;l=english" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery-1.7.1.min.js?v=3886334"></script>
    <script type="text/javascript">$J = jQuery;</script>
    <script type="text/javascript" src="http://steamcommunity-a.akamaihd.net/public/shared/javascript/shared_global.js?v=mpbggNaqEKRe&amp;l=english"></script>
    <!--<script type="text/javascript" src="http://www.dota2.com/public/javascript/lightcase/lightcase.dotahacks.js?v=.K6YQO3QNStJ-"></script>-->
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery.cycle.all.min.js?v=3886334"></script>
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery.easing.1.3.min.js?v=3886334"></script>
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/jquery.imagesloaded.min.js?v=3886334"></script>
    <script type="text/javascript" src="http://cdn.dota2.com/apps/dota2/javascript/static/spin.min.js?v=3886334"></script><style></style>
    <link href="https://fonts.googleapis.com/css?family=Cinzel:400,700" rel="stylesheet">
    <title>Dota 2 - ${changelog.patchNumber?html} Changelog</title>

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
<script language="javascript">

    function MM_preloadImages() { //v3.0
        var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
            var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }

    function PreloadHeaderImages()
    {
        //  Preload header rollover images
        //MM_preloadImages(
        //);
    }

    if ( document.addEventListener ) {
        document.addEventListener( "DOMContentLoaded", PreloadHeaderImages, false );
    } else if ( document.attachEvent ) {
        document.attachEvent( 'onDomContentLoaded', PreloadHeaderImages );
    }

</script>

<div id="BG6" class="Outer">
    <div class="Inner">
        <h2>${changelog.patchNumber?html} Gameplay Update</h2>
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

            <div id="Heroes">
                <h3>Hero Changes</h3>
                <#list changelog.heroChanges as cl>
                <figure>
                    <img data-id="${cl.name}" src="http://cdn.dota2.com/apps/dota2/images/items/${cl.name}_full.png">
                    <h1>${cl.localizedName?html}:</h1>
                    <ul>
                        <#list cl.changes as change>
                            <li>${change?html}</li>
                        </#list>
                    </ul>
                </figure>
                </#list>
            </div>
        </div>
    </div>
</div>
</body>
</html>