import $ from "expose-loader?exposes=$,jQuery!jquery";
import { concat } from "expose-loader?exposes=_.concat!lodash.concat";
import 'bootstrap';
import flatpickr from 'flatpickr';
import 'scss/app.scss';

document.querySelectorAll('.js-submit-confirm').forEach(($item) => {
    $item.addEventListener('submit', (event) => {
        if (!confirm(event.currentTarget.getAttribute('data-confirm-message'))) {
            event.preventDefault();
            return false;
        }
        return true;
    });
});

document.querySelectorAll('.js-datepicker, .js-timepicker, .js-datetimepicker').forEach(($item) => {
    const flatpickrConfig = {
        allowInput: true,
        time_24hr: true,
        enableSeconds: true
    };
    if ($item.classList.contains('js-datepicker')) {
        flatpickrConfig.dateFormat = 'Y-m-d';
    } else if ($item.classList.contains('js-timepicker')) {
        flatpickrConfig.enableTime = true;
        flatpickrConfig.noCalendar = true;
        flatpickrConfig.dateFormat = 'H:i:S';
    } else { // datetimepicker
        flatpickrConfig.enableTime = true;
        flatpickrConfig.altInput = true;
        flatpickrConfig.altFormat = 'Y-m-d H:i:S';
        flatpickrConfig.dateFormat = 'Y-m-dTH:i:S';
    }
    flatpickr($item, flatpickrConfig);
});

$("select#roles").change(function () {
    $(this).find("option:selected")
           .each(function () {
        var optionValue = $(this).attr("value");
        if (optionValue) {
            console.log('selected role: ' + optionValue);
        }
    });
}).change();

$("select#product").change(function () {
    $(this).find("option:selected")
           .each(function () {
        console.log('selected product: ' + $(this).attr("value"));
        let notCareLabel = $(this).attr("value") != 'carelabel';
        $("select#orderType").prop('disabled', notCareLabel);
        // $("select#orderType").closest('div.row').toggleClass("d-none", notCareLabel);
        let notHangtag = $(this).attr("value") != 'hangtag';
        $("input#headerFile").prop('disabled', notHangtag);
        // $("input#headerFile").closest('div.row').toggleClass("d-none", notHangtag);
    });
}).change();

$("button#addpart").click(function () {
    $("select#part").find("option:selected")
           .each(function () {
        var optionValue = $(this).attr("value");
        if (optionValue) {
            console.log('selected part: ' + optionValue);
            $("select#cilist").append($('<option/>', { 
                value: "part:" + optionValue,
                text : optionValue 
            }));
        }
    });
});

$("button#addci").click(function () {
    $("select#ci").find("option:selected")
           .each(function () {
        var optionValue = $(this).attr("value");
        if (optionValue) {
            console.log('selected ci: ' + optionValue + ' pct: ' + $("input#pct").val());
            $("select#cilist").append($('<option/>', { 
                value: "ci:" + optionValue +  ':' +  $("input#pct").val() + '%',
                text : optionValue +  ' ' +  $("input#pct").val() + '%'
            }));
        }
    });
});

$("button.cilist").click(function(){
    var $op = $("select#cilist option:selected"),
        $this = $(this);
    if($op.length){
        if ($this.val() == 'up') { 
            $op.first().prev().before($op);
        }
        if ($this.val() == 'down') { 
            $op.last().next().after($op);
        }
        if ($this.val() == 'remove') { 
            $op.remove();
        }
    }
});

$("form#ciform").on("submit", function() {
    $("select#cilist option").each(function() { 
            console.log($(this).val() + ' -> ' + $(this).text()); 
    });
    $("select#cilist option").prop('selected', 'selected')
    return true;
});