import 'fab_with_icons.dart';
import 'fab_bottom_app_bar.dart';
import 'layout.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.pink,
      ),
      home: ViewerPage(title: 'Giddy-App'),
    );
  }
}

class ViewerPage extends StatefulWidget {
  ViewerPage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _ViewerPageState createState() => _ViewerPageState();
}

class _ViewerPageState extends State<ViewerPage> with TickerProviderStateMixin {
  final GlobalKey<ScaffoldState> _scaffoldKey = new GlobalKey<ScaffoldState>();
  VoidCallback _persistentBottomSheetCallback;

  String _lastSelected = 'TAB: 0';

  void _selectedTab(int index) {
    setState(() {
      _lastSelected = 'TAB: $index';
    });
  }

  void _selectedFab(int index) {
    setState(() {
      _lastSelected = 'FAB: $index';
    });
  }

  @override
  void initState() {
    super.initState();
    _persistentBottomSheetCallback = _showBottomSheet;
  }

  void _showBottomSheet() {
    setState(() {
      _persistentBottomSheetCallback = null;
    });

    _scaffoldKey.currentState
        .showBottomSheet((context) {
          return Container(
              height: 200,
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: <Widget>[
                  Padding(
                      padding: const EdgeInsets.all(32.0),
                      child: Text(
                        'Persistent header for bottom bar!',
                        textAlign: TextAlign.left,
                      )),
                  Text(
                    'Then here there will likely be some other content '
                        'which will be displayed within the bottom bar',
                    textAlign: TextAlign.left,
                  ),
                ],
              ));
        })
        .closed
        .whenComplete(() {
          if (mounted) {
            setState(() {
              _persistentBottomSheetCallback = _showBottomSheet;
            });
          }
        });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Text(
          _lastSelected,
          style: TextStyle(fontSize: 32.0),
        ),
      ),
      bottomNavigationBar: FABBottomAppBar(
        centerItemText: "",
        color: Colors.grey,
        selectedColor: Colors.red,
        notchedShape: CircularNotchedRectangle(),
        onTabSelected: _selectedTab,
        notchMargin: 4.0,
        items: [
          FABBottomAppBarItem(iconData: Icons.verified_user, text: 'Profile'),
          FABBottomAppBarItem(iconData: Icons.history, text: 'History'),
          FABBottomAppBarItem(iconData: Icons.create, text: 'Create'),
          FABBottomAppBarItem(iconData: Icons.settings, text: 'Settings'),
        ],
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      floatingActionButton: _buildFab(context),
    );
  }

  Widget _buildFab(BuildContext context) {
    final icons = [Icons.sms, Icons.email, Icons.phone];
    return AnchoredOverlay(
      showOverlay: false,
      overlayBuilder: (context, offset) {
        return CenterAbout(
          position: Offset(offset.dx, offset.dy - icons.length * 35.0),
          child: FabWithIcons(
            icons: icons,
            onIconTapped: _selectedFab,
          ),
        );
      },
      child: FloatingActionButton(
        onPressed: () {
          _showBottomSheet();
        },
        tooltip: "Filter",
        child: Icon(Icons.sort),
        elevation: 2.0,
      ),
    );
  }
}
