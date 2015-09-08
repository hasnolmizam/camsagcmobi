$(document).ready(function(){
    //layout();
    setDynamicStyle();
    //window.onresize = function(){ document.location.reload(true); } //utk memastikan responsive bila ubah device orientation
});

function layout() {
    var h = $('.profile-name:visible').height() / 2;
    $('.profile-name:visible').css({'top':'calc(50% - '+h+'px)'});
    
    h = $('.login form').height() / 2;
    $('.login form').css({'top':'calc(50% - '+h+'px)'});
}

function setDynamicStyle() {
    $('head').append('<style id="dynamicStyle"></style>');
   
    var h = $(window).height() - 274;
    if($(window).width()>1279) h-=18;
    else if($(window).width()>959) h-=11;
    else  h+=9;
    
    $('#dynamicStyle').html(
        ".myListScroller { height:"+(h+130)+"px; } "+
        "#modalList .myListScroller { height:"+(h+130)+"px; } "
    );
}

function backPage() {
    history.go(-1);
}