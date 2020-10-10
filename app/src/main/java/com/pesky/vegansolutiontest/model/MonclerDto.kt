package com.pesky.vegansolutiontest.model

data class MonclerResponse(val status:Boolean,val lang : String,val content:List<MonclerDocument>)
data class MonclerDocument(val mediaId : Int , val mediaUrl : String,val mediaUrlBig:String,val mediaType :String,val mediaDate:MediaDate,val mediaTitleCustom : String)
{
    override fun toString(): String {
        return "MonclerDocument(mediaId=$mediaId, mediaUrl='$mediaUrl', mediaUrlBig='$mediaUrlBig', mediaType='$mediaType', mediaDate=${mediaDate}, mediaTitleCustom='$mediaTitleCustom')"
    }
}
data class MediaDate(val dateString:String,val year:String){
    override fun toString(): String {
        return "MediaDate(dateString='$dateString', year='$year')"
    }
}

/*

status	true
lang	"it"
content	0
    mediaId	20748
    mediaUrl	"https://www.monclergroup…RATEGIA-DIGITALE_ITA.pdf"
    mediaUrlBig	""
    mediaType	"pdf"
    mediaDate
        dateString	"Mon, 27 Jul 2020 00:00:00 +0000"
        year	"2020"
    mediaTitleCustom	"Moncler rafforza la sua strategia digitale"
 */