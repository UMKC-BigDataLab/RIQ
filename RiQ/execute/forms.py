from django import forms


class ExecuteForm(forms.Form):

	BTC = 'BTC'
	LOGD = 'LOGD'
	D10 = 'D10'


	MY_CHOICESDATASET = (
		(BTC, 'BTC (prebuilt)'),
# 		(LOGD, 'LOGD'),
# 		(D10,'D10-SML'),


	)

	COLDC = 'cold'
	WARMC = 'warm'

	MY_CHOICESCACHE = (
		(COLDC, 'Cold'),
		(WARMC , 'Warm'),
	)

	OPTENABLED  = 'opt'
	OPTDISABLED = 'nopt'


	MY_CHOICESOPTIMIZATION = (
		(OPTENABLED ,'Enable'),
		(OPTDISABLED ,'Disable'),

	)

	QBTC11 = 'BTC11'
	QBTC2 = 'BTC2'
	QBTC10 = 'BTC10'
	DBPDSB1 = 'DBPD1'
	DBPDSB2 = 'DBPD2'
	CUS = 'CUSTOM'

	MY_CHOICESQUERY=(
		( QBTC10,'B10'),
		( QBTC11,'B11'),
		( CUS,'CUSTOM'),
	)

	dataset = forms.ChoiceField(choices=MY_CHOICESDATASET, widget = forms.Select)
	cache = forms.ChoiceField(choices=MY_CHOICESCACHE, widget = forms.RadioSelect())

	optimize = forms.ChoiceField(choices=MY_CHOICESOPTIMIZATION, widget = forms.RadioSelect())

	query = forms.ChoiceField(choices=MY_CHOICESQUERY, widget = forms.RadioSelect())
