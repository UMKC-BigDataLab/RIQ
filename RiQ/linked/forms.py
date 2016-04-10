from django import forms
class LinkedForm(forms.Form):

	MY_CHOICESFORMAT = (
		('text', 'TEXT'),
		('csv', 'CSV'),
		('tsv','TSV'),
		('xml','XML'),
	)


	MY_CHOICES_RUNQUERY = (
		('riq', 'RIQ'),
		('jena' , 'Jena TDB'),
		('virt' , 'Virtuoso'),
	)

	QBTC1 = 'F1'
	QBTC2 = 'F2'
	QBTC3 = 'F3'
	QBTC10 = 'BTC10'
	DBPDSB1 = 'DBPD1'
	DBPDSB2 = 'DBPD2'
	CUS = 'CUSTOM'

	MY_CHOICESQUERY=(
		( QBTC1 ,'F1'),
		( QBTC2,'F2'),
# 		( QBTC10,'B10'),
# 		( DBPDSB1,'DBPD SB - 1'),
# 		( DBPDSB2,'DBPD SB - 2'),
		( QBTC3,'F3'),
		( CUS,'CUSTOM'),
	)



	COLDC = 'cold'
	WARMC = 'warm'
	outputformat = forms.ChoiceField(choices=MY_CHOICESFORMAT, widget = forms.Select)
	runsetting = forms.MultipleChoiceField(choices=MY_CHOICES_RUNQUERY, widget=forms.CheckboxSelectMultiple())
	query = forms.ChoiceField(choices=MY_CHOICESQUERY, widget = forms.RadioSelect())

