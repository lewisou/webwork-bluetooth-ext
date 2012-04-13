How to add the feature
=============

Add this line into your config.xml

    <feature id="vxmt.bluetooth.basic" required="true" version="1.0.0" />

Then everything just works.

How to use
=============

    <script>
        function call_back(mess) {
            //You can do anything else here:
            alert(mess);
        }

        var handle = vxmt.bluetooth.basic;
        handle.connect('BLUETOOTH_SERIVICE_NAME', 'call_back');
    </script>

