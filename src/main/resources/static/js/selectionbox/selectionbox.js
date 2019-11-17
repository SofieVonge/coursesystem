function SelectionBox() {
    var self = this,
        removeDoubles,
        bindClick,
        sortElementsAsc;

        /**
         * Removes doubles in selection-box-excluded & selection-box-included sections
         */
        removeDoubles = function() {
            // foreach selection-box
            // find elements existing in both excluded & included section
            // move doubles from excluded to included section
            // remove the original included double from included section
            $('.selection-box').each(function(index, selectionBox) {
                let excluded = new Array();
                let included = new Array();

                // foreach element in included section
                $(selectionBox).find('.selection-box-included').eq(0).find('.selection-box-element').each(function(index2, selectionElement) {
                    let elementValue = $(selectionElement).find('input').eq(0).val();

                    // save element to included list
                    included[elementValue] = selectionElement;
                });

                // foreach element in excluded section
                $(selectionBox).find('.selection-box-excluded').eq(0).find('.selection-box-element').each(function(index2, selectionElement) {
                    let elementValue = $(selectionElement).find('input').eq(0).val();

                    // save element to excluded list
                    excluded[elementValue] = selectionElement;
                });

                // foreach included element
                for(let key in included) {
                    let includedElement = included[key];

                    // element has double in excluded
                    if(key in excluded) {
                        let excludedElement = excluded[key];

                        // remove disabled flag from element input
                        $(excludedElement).find('input').eq(0).prop('disabled', false);

                        // move excluded element to included section
                        $(selectionBox).find('.selection-box-included').eq(0).append(excludedElement);
                    }

                    // remove included element from included section
                    $(includedElement).remove();
                }
            });
        };

        /**
         * Sort children by 'data-sort' attribute
         **/
        sortElementsAsc = function(element) {
            let children = $(element).children();

            children.sort(function (a, b) {
                let contentA =parseInt( $(a).attr('data-sort'));
                let contentB =parseInt( $(b).attr('data-sort'));
                return (contentA < contentB) ? -1 : (contentA > contentB) ? 1 : 0;
            });

            element.append(children);
        };

        /**
         * Bind click events on all 'selection-box-element's in each selection-box
         */
        bindClick = function() {
            // bind click on selection-box-element
            $('.selection-box-element').on('click', function() {
                let included = $(this).parent().prop('className') == 'selection-box-included' ? true : false;
                let selectionBox = $(this).closest('.selection-box');

                if(included === false) {
                    $(selectionBox).find('.selection-box-included').eq(0).append(this);

                } else {
                    $(selectionBox).find('.selection-box-excluded').eq(0).append(this);
                }

                $(this).find('input').prop('disabled', (i, v) => !v);

                sortElementsAsc($(this).parent());
            });
        };

        /**
         * Initialization should be called upon once
         */
        this.init = function() {
            removeDoubles();

            // sort excluded & included sections in each selection-box
            $('.selection-box').each(function() {
                let excludedSection = $(this).find('.selection-box-excluded').eq(0);
                let includedSection = $(this).find('.selection-box-included').eq(0);

                sortElementsAsc(excludedSection);
                sortElementsAsc(includedSection);
            });

            bindClick();
        };
}